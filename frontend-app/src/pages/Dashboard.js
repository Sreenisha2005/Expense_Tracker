import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";

const Dashboard = () => {
  const [data, setData] = useState([]);

  const loadData = async () => {
    const res = await api.get("/expenses/summary");
    setData(res.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const colors = ["#8884d8", "#82ca9d", "#ffc658", "#ff6b6b", "#00C49F"];

  return (
    <div className="dashboard">
      <h2>Expense Summary</h2>
      <PieChart width={400} height={300}>
        <Pie
          data={data}
          dataKey="amount"
          nameKey="category"
          outerRadius={120}
          fill="#8884d8"
        >
          {data.map((_, index) => (
            <Cell key={index} fill={colors[index % colors.length]} />
          ))}
        </Pie>
        <Tooltip />
        <Legend />
      </PieChart>
    </div>
  );
};

export default Dashboard;
