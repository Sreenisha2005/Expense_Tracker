import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";

const Expenses = () => {
  const [expenses, setExpenses] = useState([]);

  const loadExpenses = async () => {
    const res = await api.get("/expenses");
    setExpenses(res.data);
  };

  useEffect(() => {
    loadExpenses();
  }, []);

  const deleteExpense = async (id) => {
    await api.delete(`/expenses/${id}`);
    loadExpenses();
  };

  return (
    <div className="container">
      <h2>Your Expenses</h2>
      <table>
        <thead>
          <tr><th>Category</th><th>Amount</th><th>Date</th><th>Action</th></tr>
        </thead>
        <tbody>
          {expenses.map((exp) => (
            <tr key={exp.id}>
              <td>{exp.category}</td>
              <td>{exp.amount}</td>
              <td>{exp.date}</td>
              <td><button onClick={() => deleteExpense(exp.id)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Expenses;