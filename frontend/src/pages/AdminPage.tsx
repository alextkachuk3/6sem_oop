import React, {useEffect, useState} from "react";
import {useKeycloak} from "@react-keycloak/web";
import {useNavigate} from "react-router-dom";

type CurrentAdmin = {
    id: number;
    username: string;
};
type Client = {
    id: number;
    username: string;
    amount: number;
    disable: boolean;
};
type Goods = {
    id: string;
    name: string;
    price: string;
    count: number;
};

export const AdminPage = () => {
    const {keycloak} = useKeycloak();
    const navigate = useNavigate();
    const [currentAdmin, setCurrentAdmin] = useState<CurrentAdmin>();
    const [goods, setGoods] = useState<Goods[]>();
    const [clients, setClients] = useState<Client[]>();

    const updateScreen = () => {
        fetch("http://localhost:8080/servlet_war_exploded/current-admin", {
            headers: {Authorization: "Bearer " + keycloak.token},
        })
            .then((r) => r.json())
            .then(setCurrentAdmin)
            .catch(console.error);
        fetch("http://localhost:8080/servlet_war_exploded/goods")
            .then((r) => r.json())
            .then(setGoods)
            .catch(console.error);
        fetch("http://localhost:8080/servlet_war_exploded/clients", {
            headers: {Authorization: "Bearer " + keycloak.token},
        })
            .then((r) => r.json())
            .then(setClients)
            .catch(console.error);
    };

    useEffect(() => {
        setTimeout(() => {
            if (!keycloak.authenticated) {
                navigate("/");
                return;
            }
            updateScreen();
        }, 300);
    }, []);

    if (!currentAdmin || !goods || !clients) {
        return <h1>Loading...</h1>;
    }

    return (
        <>
            <h1>
                {currentAdmin.username}
            </h1>
            <button onClick={() => keycloak.logout()}>Logout</button>
            <table className="table table-striped table-hover" id="goods">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Count exist</th>
                    <th>Refresh</th>
                </tr>
                </thead>
                <tbody>
                {goods.map((g) => (
                    <tr key={"tr_" + g.id}>
                        <td>{g.name}</td>
                        <td>{g.price}</td>
                        <td>{g.count}</td>
                        <td>
                            <input type={"number"} id={"goods_" + g.id} min={0}/>
                            <button
                                onClick={() => {
                                    // @ts-ignore
                                    const count = document.getElementById("goods_" + g.id).value;
                                    fetch(
                                        "http://localhost:8080/servlet_war_exploded/refresh",
                                        {
                                            method: "PUT",
                                            headers: {
                                                Authorization: "Bearer " + keycloak.token,
                                                "Content-Type": "application/json",
                                            },
                                            body: JSON.stringify({
                                                id: g.id,
                                                count: count,
                                            }),
                                        }
                                    )
                                        .then(() => updateScreen())
                                        .catch(console.error);
                                }}
                            >
                                Add
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            Clients
            <table className="table table-striped table-hover" id="clients">
                <thead>
                <tr>
                    <th>id</th>
                    <th>username</th>
                    <th>Amount</th>
                    <th>disabled</th>
                    <th>action</th>
                </tr>
                </thead>
                <tbody>
                {clients.map((client) => (
                    <tr key={"tr_" + client.id}>
                        <td>{client.id}</td>
                        <td>{client.username}</td>
                        <td>{client.amount}</td>
                        <td>{String(client.disable)}</td>
                        <td>
                            {client.disable ? <button
                                onClick={() => fetch(
                                    "http://localhost:8080/servlet_war_exploded/enable",
                                    {
                                        method: "POST",
                                        headers: {
                                            Authorization: "Bearer " + keycloak.token,
                                            "Content-Type": "application/json",
                                        },
                                        body: JSON.stringify({
                                            id: client.id
                                        }),
                                    }
                                )
                                    .then(() => updateScreen())
                                    .catch(console.error)}
                            >
                                enable
                            </button> : <button
                                onClick={() => fetch(
                                    "http://localhost:8080/servlet_war_exploded/disable",
                                    {
                                        method: "POST",
                                        headers: {
                                            Authorization: "Bearer " + keycloak.token,
                                            "Content-Type": "application/json",
                                        },
                                        body: JSON.stringify({
                                            id: client.id
                                        }),
                                    }
                                )
                                    .then(() => updateScreen())
                                    .catch(console.error)}
                            >
                                disable
                            </button>}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
};
