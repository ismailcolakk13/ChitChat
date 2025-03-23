import axios from "axios";
import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../services/UserProvider";

const Login = () => {
  const { setUser } = useContext(UserContext);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "/api/login",
        { username, password },
        { withCredentials: true }
      );
      setUser(response.data);
      localStorage.setItem("user", JSON.stringify(response.data));
      console.log("login success!", response.data);
      navigate("/");
    } catch (error) {
      console.log("login error!!!", error);
    }
  };
  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center">
      <div className="w-full max-w-md p-8 bg-gray-800 rounded-lg shadow-lg">
        <h2 className="text-3xl font-semibold text-center text-white mb-6">
          Sign In
        </h2>
        <form onSubmit={handleLogin} className="space-y-4">
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
                autoComplete="current-password"
              />
            </label>
          </div>
          <div>
            <button
              type="submit"
              className="w-full py-3 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            >
              Sign In
            </button>
          </div>
        </form>
        <p className="mt-6 text-center text-sm text-gray-400">Don't have an account?{" "}
        <button type="button" onClick={() => navigate("/register")} className="text-indigo-500 hover:text-indigo-600">
          Register here
        </button>
        </p>
      </div>
    </div>
  );
};

export default Login;
