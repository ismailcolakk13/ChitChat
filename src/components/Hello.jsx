import React, { useContext } from "react";
import { UserContext } from "../services/UserProvider";

const Hello = () => {
  const { user, setUser } = useContext(UserContext);
  const handleLogout = async (e) => {
    e.preventDefault();
    try {
      await fetch("/api/logout", {
        method: "POST",
        credentials: "include",
      });
      setUser(null);
      localStorage.removeItem("user");
      console.log("Successfully logged out!");
    } catch (error) {
      console.error("Logout failed", error);
    }
  };
  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center">
      <div className="w-full max-w-md p-8 bg-gray-800 rounded-lg shadow-lg">
        <h1 className="text-3xl font-semibold text-center text-white mb-6">
          Hello, {user?.username}!
        </h1>
        <p className="text-center text-white text-lg mb-4">Age: {user?.age}</p>
        <form onSubmit={handleLogout} className="text-center">
          <button
            type="submit"
            className="w-full py-3 bg-red-600 text-white rounded-md hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500"
          >
            Sign Out
          </button>
        </form>
      </div>
    </div>
  );
};

export default Hello;
