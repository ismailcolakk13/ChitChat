import { useContext } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Hello from "./components/hello";
import Login from "./components/login";
import Register from "./components/Register";
import { UserContext } from "./services/UserProvider";

function App() {
  const { user } = useContext(UserContext);
  return (
    <>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/" element={user ? <Hello /> : <Navigate to="/login" />} />
      </Routes>
    </>
  );
}

export default App;
