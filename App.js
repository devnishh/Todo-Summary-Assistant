import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TodoList from './components/TodoList';
import TodoForm from './components/TodoForm';
import './App.css';

const App = () => {
  const [todos, setTodos] = useState([]);
  const [message, setMessage] = useState('');

  // Fetch all todos on mount
  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const response = await axios.get(`${process.env.REACT_APP_API_URL}/todos`);
      setTodos(response.data);
    } catch (error) {
      console.error('Error fetching todos:', error);
    }
  };

  const addTodo = async (text) => {
    try {
      const response = await axios.post(`${process.env.REACT_APP_API_URL}/todos`, { text });
      setTodos([...todos, response.data]);
    } catch (error) {
      console.error('Error adding todo:', error);
    }
  };

  const deleteTodo = async (id) => {
    try {
      await axios.delete(`${process.env.REACT_APP_API_URL}/todos/${id}`);
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (error) {
      console.error('Error deleting todo:', error);
    }
  };

  const editTodo = async (id, newText) => {
    try {
      const response = await axios.post(`${process.env.REACT_APP_API_URL}/todos`, { id, text: newText });
      setTodos(todos.map((todo) => (todo.id === id ? response.data : todo)));
    } catch (error) {
      console.error('Error editing todo:', error);
    }
  };

  const summarizeTodos = async () => {
    try {
      const response = await axios.post(`${process.env.REACT_APP_API_URL}/summarize`);
      setMessage('Summary sent to Slack successfully!');
    } catch (error) {
      setMessage('Failed to send summary to Slack.');
      console.error('Error summarizing todos:', error);
    }
    // Clear message after 3 seconds
    setTimeout(() => setMessage(''), 3000);
  };

  return (
    <div className="app">
      <h1>Todo Summary Assistant</h1>
      <TodoForm addTodo={addTodo} />
      <TodoList todos={todos} deleteTodo={deleteTodo} editTodo={editTodo} />
      <button className="summarize-btn" onClick={summarizeTodos}>
        Summarize and Send to Slack
      </button>
      {message && <p className={`message ${message.includes('Failed') ? 'error' : 'success'}`}>{message}</p>}
    </div>
  );
};

export default App;