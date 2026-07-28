<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Portfolio Dashboard</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 2rem;
      max-width: 600px;
    }
    .form-group {
      margin-bottom: 1.5rem;
    }
    label {
      display: block;
      font-weight: bold;
      margin-bottom: 0.5rem;
    }
    select {
      width: 100%;
      padding: 0.5rem;
      font-size: 1rem;
    }
    .card {
      border: 1px solid #ccc;
      border-radius: 8px;
      padding: 1rem;
      background-color: #f9f9f9;
    }
    pre {
      background: #eee;
      padding: 10px;
      border-radius: 4px;
      overflow-x: auto;
    }
  </style>
</head>
<body>

  <h2>Stock Portfolio Overview</h2>

  <!-- Dropdown Section -->
  <div class="form-group">
    <label for="positionSelect">Select a Position / Symbol:</label>
    <select id="positionSelect">
      <option value="">-- Select an option --</option>
    </select>
  </div>

  <!-- Detail Display Section -->
  <div id="detailContainer" class="card" style="display: none;">
    <h3>Position Detail</h3>
    <div id="detailContent">Loading details...</div>
  </div>

  <script>
    const BASE_URL = '${sessionScope.apiBaseUrl}'; // Ensure this is set in your JSP session scope
    const positionSelect = document.getElementById('positionSelect');
    const detailContainer = document.getElementById('detailContainer');
    const detailContent = document.getElementById('detailContent');

    // 1. Fetch Summary to populate the dropdown on page load
    async function loadSummary() {
      try {
        const response = await fetch(`${BASE_URL}/summary`);
        if (!response.ok) throw new Error('Failed to fetch summary data');
        
        const data = await response.json();
        
        // Assuming `data` is an array of positions containing `symbol` or `id`
        data.forEach(item => {
          const option = document.createElement('option');
          // Adjust property names (e.g., item.symbol, item.id) to match your API response schema
          option.value = item.symbol || item.id;
          option.textContent = `${item.symbol || item.id} - ${item.name || 'Position'}`;
          positionSelect.appendChild(option);
        });
      } catch (error) {
        console.error('Error loading summary:', error);
      }
    }

    // 2. Fetch Detail when a user selects a dropdown option
    async function fetchDetail(selectedId) {
      if (!selectedId) {
        detailContainer.style.display = 'none';
        return;
      }

      detailContainer.style.display = 'block';
      detailContent.innerHTML = '<em>Loading...</em>';

      try {
        // Pass selected item ID/symbol as a query parameter (e.g., /detail?symbol=XYZ or /detail?id=123)
        const response = await fetch(`${BASE_URL}/detail?symbol=${encodeURIComponent(selectedId)}`);
        if (!response.ok) throw new Error('Failed to fetch detail data');
        
        const detailData = await response.json();

        // Render response data (formatted as formatted JSON for demonstration)
        detailContent.innerHTML = `<pre>${JSON.stringify(detailData, null, 2)}</pre>`;
      } catch (error) {
        console.error('Error fetching details:', error);
        detailContent.innerHTML = `<p style="color: red;">Error loading position details.</p>`;
      }
    }

    // Event Listener
    positionSelect.addEventListener('change', (e) => {
      fetchDetail(e.target.value);
    });

    // Initial call
    loadSummary();
  </script>
</body>
</html>