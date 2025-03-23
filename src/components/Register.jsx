import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("USER");
  const [age, setAge] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "/api/register",
        { username, password, role, age },
        { withCredentials: true }
      );
      console.log("register success!", response.data);
      navigate("/login");
    } catch (error) {
      console.log("register error!!!", error);
    }
  };
  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center">
      <div className="w-full max-w-md p-8 bg-gray-800 rounded-lg shadow-lg">
        <h2 className="text-3xl font-semibold text-center text-white mb-6">
          Register
        </h2>

        <form onSubmit={handleRegister} className="space-y-4">
          <div>
            <label
              className="block text-white text-sm font-medium mb-2"
              htmlFor="username"
            >
              User Name :
              <input
                type="text"
                id="username"
                name="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="w-full p-3 bg-gray-700 text-white rounded-md border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                required
                autoComplete="username"
              />
            </label>
          </div>
          <div>
            <label
              className="block text-white text-sm font-medium mb-2"
              htmlFor="password"
            >
              Password:
              <input
                type="password"
                id="password"
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full p-3 bg-gray-700 text-white rounded-md border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                required
                autoComplete="new-password"
              />
            </label>
          </div>
          <div>
            <label
              className="block text-white text-sm font-medium mb-2"
              htmlFor="role"
            >
              Role:
              <select
                name="role"
                id="role"
                value={role}
                onChange={(e) => setRole(e.target.value)}
                className="w-full p-3 bg-gray-700 text-white rounded-md border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                required
              >
                <option value="USER">User</option>
                <option value="ADMIN">Admin</option>
              </select>
            </label>
          </div>
          <div>
            <label
              className="block text-white text-sm font-medium mb-2"
              htmlFor="age"
            >
              Age:
              <input
                type="number"
                pattern="\d*"
                id="age"
                name="age"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                className="w-full p-3 bg-gray-700 text-white rounded-md border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                required
                autoComplete="off"
              />
            </label>
          </div>
          <div>
            <button
              type="submit"
              className="w-full p-3 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            >
              Register
            </button>
          </div>
        </form>
        <p className="mt-6 text-center text-sm text-gray-400">
          Already have an account?{" "}
          <button
            type="button"
            onClick={() => navigate("/login")}
            className="text-indigo-500 hover:text-indigo-600"
          >
            Login here
          </button>
        </p>
      </div>
    </div>
  );
};

export default Register;
