import Home from "./pages/Home";
import React from 'react'
import {
    BrowserRouter,
    Route, Routes
} from 'react-router-dom'
import Login from "./pages/Login/Login";
import Currency from "./context/Currecny";
import Auth from "./auth/Auth";

function App() {
  return (
      <Auth>
          <Currency>
              <BrowserRouter>
                  <Routes>
                      <Route index element={<Home />} />
                      <Route path='/login' element={<Login />} />
                  </Routes>
              </BrowserRouter>
          </Currency>
      </Auth>
  );
}

export default App;
