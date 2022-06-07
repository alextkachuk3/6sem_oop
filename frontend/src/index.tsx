import React from "react";
import ReactDOM from "react-dom";
import "./bootstrap.min.css";
import keycloak from "./Keycloak";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Home} from "./pages/HomPage";
import {ClientPage} from "./pages/ClientPage";
import {AdminPage} from "./pages/AdminPage";
import {ReactKeycloakProvider} from "@react-keycloak/web";

ReactDOM.render(
    <ReactKeycloakProvider authClient={keycloak}>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/client" element={<ClientPage/>}/>
                <Route path="/admin" element={<AdminPage/>}/>
            </Routes>
        </BrowserRouter>
    </ReactKeycloakProvider>,
    document.getElementById("root")
);
