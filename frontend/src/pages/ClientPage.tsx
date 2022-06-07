import React, {useEffect, useState} from "react";
import {useKeycloak} from "@react-keycloak/web";
import {useNavigate} from "react-router-dom";

type CurrentClient = {
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

export const ClientPage = () => {
    const {keycloak} = useKeycloak();
    const navigate = useNavigate();
    const [currentClient, setCurrentClient] = useState<CurrentClient>();
    const [goods, setGoods] = useState<Goods[]>();

    const updateScreen = () => {
        fetch("http://localhost:8080/servlet_war_exploded/current-client", {
            method: "GET",
            headers: {Authorization: "Bearer " + keycloak.token},
        })
            .then((r) => r.json())
            .then(client => {
                setCurrentClient(client);
            })
            .catch(console.error);
        fetch("http://localhost:8080/servlet_war_exploded/goods")
            .then((r) => r.json())
            .then(setGoods)
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

    if (!currentClient || !goods) {
        return <h1>Loading...</h1>;
    }

    if (currentClient.disable) {
        return <>
            <h1>
                {currentClient.username}: {currentClient.amount}$
            </h1>
            <button onClick={() => keycloak.logout()}>Logout</button>
            <h2 style={{color: "red"}}>Your account disabled</h2>
        </>;
    }

    return (
        <>
            <h1>
                {currentClient.username}: {currentClient.amount}$
            </h1>
            <button onClick={() => keycloak.logout()}>Logout</button>
            <table className="table table-striped table-hover" id="goods">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Count exist</th>
                    <th>Buy</th>
                </tr>
                </thead>
                <tbody>
                {goods.map((d) => (
                    <tr key={"tr_" + d.id}>
                        <td>{d.name}</td>
                        <td>{d.price}</td>
                        <td>{d.count}</td>
                        <td>
                            <input type={"number"} id={"goods_" + d.id} min={0}/>
                            <button
                                onClick={() => {
                                    // @ts-ignore
                                    const count = document.getElementById("goods_" + d.id).value;
                                    fetch(
                                        "http://localhost:8080/servlet_war_exploded/order",
                                        {
                                            method: "POST",
                                            headers: {
                                                Authorization: "Bearer " + keycloak.token,
                                                "Content-Type": "application/json",
                                            },
                                            body: JSON.stringify({
                                                id: d.id,
                                                count: count,
                                            }),
                                        }
                                    )
                                        .then(() => updateScreen())
                                        .catch(console.error);
                                }}
                            >
                                Order
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
};
