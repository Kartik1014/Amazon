import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { sweetService } from '../services/api';
import SweetCard from '../components/SweetCard';
import SearchBar from '../components/SearchBar';
import AddSweetModal from '../components/AddSweetModal';
import './Dashboard.css';

function Dashboard() {
  const { user, logout, isAdmin } = useAuth();
  const [sweets, setSweets] = useState([]);
  const [filteredSweets, setFilteredSweets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showAddModal, setShowAddModal] = useState(false);

  useEffect(() => {
    fetchSweets();
  }, []);

  const fetchSweets = async () => {
    try {
      setLoading(true);
      const data = await sweetService.getAllSweets();
      setSweets(data);
      setFilteredSweets(data);
      setError('');
    } catch (err) {
      setError('Failed to load sweets. Please try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (searchParams) => {
    try {
      setLoading(true);
      const data = await sweetService.searchSweets(searchParams);
      setFilteredSweets(data);
    } catch (err) {
      setError('Search failed. Please try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handlePurchase = async (id, quantity) => {
    try {
      await sweetService.purchaseSweet(id, quantity);
      fetchSweets();
      return { success: true };
    } catch (err) {
      return { success: false, error: err.response?.data || 'Purchase failed' };
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this sweet?')) {
      try {
        await sweetService.deleteSweet(id);
        fetchSweets();
      } catch (err) {
        alert('Failed to delete sweet: ' + (err.response?.data || 'Unknown error'));
      }
    }
  };

  const handleAddSweet = async (sweetData) => {
    try {
      await sweetService.createSweet(sweetData);
      fetchSweets();
      setShowAddModal(false);
      return { success: true };
    } catch (err) {
      return { success: false, error: err.response?.data || 'Failed to add sweet' };
    }
  };

  const handleRestock = async (id, quantity) => {
    try {
      await sweetService.restockSweet(id, quantity);
      fetchSweets();
      return { success: true };
    } catch (err) {
      return { success: false, error: err.response?.data || 'Restock failed' };
    }
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>🍬 Sweet Shop</h1>
        <div className="header-actions">
          <span className="welcome-text">Welcome, {user?.username}!</span>
          {isAdmin() && <span className="admin-badge">Admin</span>}
          <button onClick={logout} className="btn-logout">Logout</button>
        </div>
      </header>

      <div className="dashboard-content">
        <div className="dashboard-controls">
          <SearchBar onSearch={handleSearch} />
          {isAdmin() && (
            <button onClick={() => setShowAddModal(true)} className="btn-add">
              + Add New Sweet
            </button>
          )}
        </div>

        {error && <div className="error-message">{error}</div>}

        {loading ? (
          <div className="loading">Loading sweets...</div>
        ) : filteredSweets.length === 0 ? (
          <div className="no-sweets">No sweets found. {isAdmin() && 'Add some to get started!'}</div>
        ) : (
          <div className="sweets-grid">
            {filteredSweets.map((sweet) => (
              <SweetCard
                key={sweet.id}
                sweet={sweet}
                onPurchase={handlePurchase}
                onDelete={isAdmin() ? handleDelete : null}
                onRestock={isAdmin() ? handleRestock : null}
              />
            ))}
          </div>
        )}
      </div>

      {showAddModal && (
        <AddSweetModal
          onClose={() => setShowAddModal(false)}
          onAdd={handleAddSweet}
        />
      )}
    </div>
  );
}

export default Dashboard;
