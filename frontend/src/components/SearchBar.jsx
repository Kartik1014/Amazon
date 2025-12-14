import React, { useState } from 'react';
import './SearchBar.css';

function SearchBar({ onSearch }) {
  const [searchParams, setSearchParams] = useState({
    name: '',
    category: '',
    minPrice: '',
    maxPrice: '',
  });

  const handleChange = (e) => {
    setSearchParams({ ...searchParams, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const params = {};
    if (searchParams.name) params.name = searchParams.name;
    if (searchParams.category) params.category = searchParams.category;
    if (searchParams.minPrice) params.minPrice = parseFloat(searchParams.minPrice);
    if (searchParams.maxPrice) params.maxPrice = parseFloat(searchParams.maxPrice);
    onSearch(params);
  };

  const handleReset = () => {
    setSearchParams({
      name: '',
      category: '',
      minPrice: '',
      maxPrice: '',
    });
    onSearch({});
  };

  return (
    <form onSubmit={handleSubmit} className="search-bar">
      <input
        type="text"
        name="name"
        value={searchParams.name}
        onChange={handleChange}
        placeholder="Search by name..."
        className="search-input"
      />
      <input
        type="text"
        name="category"
        value={searchParams.category}
        onChange={handleChange}
        placeholder="Category..."
        className="search-input"
      />
      <input
        type="number"
        name="minPrice"
        value={searchParams.minPrice}
        onChange={handleChange}
        placeholder="Min price"
        step="0.01"
        className="search-input price-input"
      />
      <input
        type="number"
        name="maxPrice"
        value={searchParams.maxPrice}
        onChange={handleChange}
        placeholder="Max price"
        step="0.01"
        className="search-input price-input"
      />
      <button type="submit" className="btn-search">Search</button>
      <button type="button" onClick={handleReset} className="btn-reset">Reset</button>
    </form>
  );
}

export default SearchBar;
