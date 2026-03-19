import { useState, useEffect } from 'react';
import './App.css';

const BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
const API = `${BASE_URL}/api/v1/player`;
const CHAT_API = `${BASE_URL}/api/v1/chat`;

function App() {
  const [players, setPlayers] = useState([]);
  const [search, setSearch] = useState('');
  const [teamFilter, setTeamFilter] = useState('');
  const [aiQuery, setAiQuery] = useState('');
  const [loading, setLoading] = useState(true);
  const [aiLoading, setAiLoading] = useState(false);
  const [error, setError] = useState(null);
  const [aiLabel, setAiLabel] = useState(null);

  useEffect(() => {
    fetchPlayers();
  }, []);

  function fetchPlayers() {
    setLoading(true);
    setError(null);
    setAiLabel(null);
    fetch(API)
      .then(res => res.json())
      .then(data => {
        setPlayers(data);
        setLoading(false);
      })
      .catch(() => {
        setError('Could not connect to the server. Make sure Spring Boot is running.');
        setLoading(false);
      });
  }

  function handleSearch(e) {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setAiLabel(null);

    let url = API;
    if (search) url += `?name=${search}`;
    else if (teamFilter) url += `?team=${teamFilter}`;

    fetch(url)
      .then(res => res.json())
      .then(data => {
        setPlayers(data);
        setLoading(false);
      })
      .catch(() => {
        setError('Search failed.');
        setLoading(false);
      });
  }

  function handleAiSearch(e) {
    e.preventDefault();
    if (!aiQuery.trim()) return;

    setAiLoading(true);
    setError(null);
    setAiLabel(null);

    fetch(CHAT_API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ query: aiQuery }),
    })
      .then(res => {
        if (!res.ok) return res.text().then(t => { throw new Error(t); });
        return res.json();
      })
      .then(data => {
        setPlayers(data);
        setAiLabel(`AI result for: "${aiQuery}"`);
        setAiLoading(false);
      })
      .catch(err => {
        setError('AI search failed: ' + err.message);
        setAiLoading(false);
      });
  }

  function handleReset() {
    setSearch('');
    setTeamFilter('');
    setAiQuery('');
    fetchPlayers();
  }

  return (
    <div className="app">
      <header className="app-header">
        <h1>NBA Player Stats 2024-25 Season</h1>
      </header>

      <div className="controls">
        <form onSubmit={handleAiSearch} className="ai-search-form">
          <div className="ai-badge">AI</div>
          <input
            type="text"
            placeholder='Ask anything, e.g. "Lakers players over 20 points"'
            value={aiQuery}
            onChange={e => setAiQuery(e.target.value)}
          />
          <button type="submit" disabled={aiLoading}>
            {aiLoading ? 'Loading' : 'Enter'}
          </button>
        </form>

        <form onSubmit={handleSearch} className="search-form">
          <input
            type="text"
            placeholder="Search by player name"
            value={search}
            onChange={e => setSearch(e.target.value)}
          />
          <input
            type="text"
            placeholder="Filter by team (e.g. LAL)"
            value={teamFilter}
            onChange={e => setTeamFilter(e.target.value)}
          />
          <button type="submit">Search</button>
          <button type="button" onClick={handleReset}>Reset</button>
        </form>
      </div>

      {aiLabel && <p className="ai-label">{aiLabel}</p>}
      {(loading || aiLoading) && <p className="status">Loading...</p>}
      {error && <p className="status error">{error}</p>}
      {!loading && !aiLoading && !error && players.length === 0 && (
        <p className="status">No players found.</p>
      )}

      {!loading && !aiLoading && !error && players.length > 0 && (
        <div className="table-wrapper">
          <p className="count">{players.length} players</p>
          <table>
            <thead>
              <tr>
                <th>Rank</th>
                <th>Player</th>
                <th>Team</th>
                <th>GP</th>
                <th>MIN</th>
                <th>PTS</th>
                <th>REB</th>
                <th>AST</th>
                <th>STL</th>
                <th>BLK</th>
                <th>FG%</th>
                <th>3P%</th>
                <th>FT%</th>
                <th>EFF</th>
              </tr>
            </thead>
            <tbody>
              {players.map(p => (
                <tr key={p.player_id}>
                  <td>{p.rank}</td>
                  <td className="player-name">{p.player}</td>
                  <td>{p.team}</td>
                  <td>{p.gp}</td>
                  <td>{p.min}</td>
                  <td><strong>{p.pts}</strong></td>
                  <td>{p.reb}</td>
                  <td>{p.ast}</td>
                  <td>{p.stl}</td>
                  <td>{p.blk}</td>
                  <td>{(p.fg_pct * 100).toFixed(1)}%</td>
                  <td>{(p.fg3_pct * 100).toFixed(1)}%</td>
                  <td>{(p.ft_pct * 100).toFixed(1)}%</td>
                  <td>{p.eff}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default App;
