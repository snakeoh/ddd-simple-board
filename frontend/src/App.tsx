// import { useState } from "react";
// import reactLogo from "./assets/react.svg";
// import viteLogo from "/vite.svg";
// import "./App.css";
// ///////////////////////////////////////////
import { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import PostDetailPage from "./pages/PostDetailPage";
import PostListPage from "./pages/PostListPage";
import Header from "./components/Header";
// import { ErrorBoundary } from "./components/ErrorBoundary";

function App() {
    // const [count, setCount] = useState(0);

    // return (
    //   <>
    //     <div>
    //       <a href="https://vite.dev" target="_blank">
    //         <img src={viteLogo} className="logo" alt="Vite logo" />
    //       </a>
    //       <a href="https://react.dev" target="_blank">
    //         <img src={reactLogo} className="logo react" alt="React logo" />
    //       </a>
    //     </div>
    //     <h1>Vite + React</h1>
    //     <div className="card">
    //       <button onClick={() => setCount((count) => count + 1)}>
    //         count is {count}
    //       </button>
    //       <p>
    //         Edit <code>src/App.tsx</code> and save to test HMR
    //       </p>
    //     </div>
    //     <p className="read-the-docs">
    //       Click on the Vite and React logos to learn more
    //     </p>
    //   </>
    // )
    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem("accessToken") !== null);

    useEffect(() => {
        const handleStorage = () => {
            setIsLoggedIn(localStorage.getItem("accessToken") !== null);
        };
        window.addEventListener("storage", handleStorage);
        return () => window.removeEventListener("storage", handleStorage);
    }, []);

    return (
        <BrowserRouter>
            <Header />
            <Routes>
                <Route path="/" element={isLoggedIn ? <Navigate to="/posts" /> : <LoginPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/posts" element={isLoggedIn ? <PostListPage /> : <Navigate to="/" />} />
                <Route path="/posts/:postId" element={isLoggedIn ? <PostDetailPage /> : <Navigate to="/" />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
