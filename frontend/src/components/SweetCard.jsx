import React, { useState } from 'react';
import './SweetCard.css';

function SweetCard({ sweet, onPurchase, onDelete, onRestock }) {
  const [purchaseQty, setPurchaseQty] = useState(1);
  const [restockQty, setRestockQty] = useState(1);
  const [purchasing, setPurchasing] = useState(false);
  const [restocking, setRestocking] = useState(false);

  const handlePurchase = async () => {
    if (purchaseQty > sweet.quantity) {
      alert('Not enough quantity available');
      return;
    }
    if (purchaseQty <= 0) {
      alert('Please enter a valid quantity');
      return;
    }

    setPurchasing(true);
    const result = await onPurchase(sweet.id, purchaseQty);
    setPurchasing(false);

    if (result.success) {
      setPurchaseQty(1);
    } else {
      alert(result.error);
    }
  };

  const handleRestock = async () => {
    if (restockQty <= 0) {
      alert('Please enter a valid quantity');
      return;
    }

    setRestocking(true);
    const result = await onRestock(sweet.id, restockQty);
    setRestocking(false);

    if (result.success) {
      setRestockQty(1);
    } else {
      alert(result.error);
    }
  };

  const isOutOfStock = sweet.quantity === 0;

  return (
    <div className={`sweet-card ${isOutOfStock ? 'out-of-stock' : ''}`}>
      <div className="sweet-header">
        <h3>{sweet.name}</h3>
        {onDelete && (
          <button onClick={() => onDelete(sweet.id)} className="btn-delete" title="Delete">
            🗑️
          </button>
        )}
      </div>
      
      <div className="sweet-category">{sweet.category}</div>
      <div className="sweet-price">${sweet.price.toFixed(2)}</div>
      <div className={`sweet-quantity ${isOutOfStock ? 'zero' : ''}`}>
        Stock: {sweet.quantity}
        {isOutOfStock && <span className="out-of-stock-badge">OUT OF STOCK</span>}
      </div>

      <div className="sweet-actions">
        <div className="purchase-section">
          <input
            type="number"
            min="1"
            max={sweet.quantity}
            value={purchaseQty}
            onChange={(e) => setPurchaseQty(parseInt(e.target.value) || 1)}
            disabled={isOutOfStock}
            className="quantity-input"
          />
          <button
            onClick={handlePurchase}
            disabled={isOutOfStock || purchasing}
            className="btn-purchase"
          >
            {purchasing ? 'Purchasing...' : 'Purchase'}
          </button>
        </div>

        {onRestock && (
          <div className="restock-section">
            <input
              type="number"
              min="1"
              value={restockQty}
              onChange={(e) => setRestockQty(parseInt(e.target.value) || 1)}
              className="quantity-input"
              placeholder="Qty"
            />
            <button
              onClick={handleRestock}
              disabled={restocking}
              className="btn-restock"
            >
              {restocking ? 'Restocking...' : 'Restock'}
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

export default SweetCard;
