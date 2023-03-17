import Home from "./pages/Home/Home";
import React from 'react'
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import Login from "./pages/Login/Login";
import Currency from "./context/Currecny";
import Auth from "./auth/Auth";
import UnauthorizedRoutes from "./util/UnauthorizedRoutes";
import AuthorizedRoutes from "./util/AuthorizedRoutes";
import ClientHome from "./pages/Client-Home/Client-Home";
import Registration from "./pages/Registration/Registration";
function App() {
    return (
         <Auth>
             <Currency>
                 <BrowserRouter>
                     <UnauthorizedRoutes>
                         <Routes>
                             <Route path="*" element={<Navigate to="/" />} />
                             <Route path="/" element={<Home/>} />
                             <Route path='/login' element={<Login />} />
                             <Route path='/registration' element={<Registration/>}/>
                         </Routes>
                     </UnauthorizedRoutes>
                     <AuthorizedRoutes>
                         <Routes>
                             <Route path="*" element={<Navigate to="/" />} />
                             <Route path='/' element={<ClientHome />} />
                         </Routes>
                     </AuthorizedRoutes>
                 </BrowserRouter>
             </Currency>
         </Auth>
    );
}

export default App;
