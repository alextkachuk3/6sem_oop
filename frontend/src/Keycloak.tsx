import Keycloak from "keycloak-js";

const keycloak = Keycloak({
    url: "http://localhost:8000",
    realm: "myrealm",
    clientId: "myclient"
});

export default keycloak;
