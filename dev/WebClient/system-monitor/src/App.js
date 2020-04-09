import React, { Component } from "react";
import { ApolloClient } from "apollo-client";
import { InMemoryCache } from "apollo-cache-inmemory";
import { ApolloProvider } from "react-apollo";
import { RestLink } from "apollo-link-rest";
import "./App.css";
import MyRouter from "./MyRouter";
const restLink = new RestLink({ uri: "http://13.125.208.19/" });

const client = new ApolloClient({
    link: restLink,
    cache: new InMemoryCache(),
    fetchOptions: { mode: "no-cors" },
});

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: "null",
            authenticated: false,
            isPollin: false,
        };
    }

    componentDidMount() {
        const sessionUser = window.sessionStorage.getItem("user");
        const sessionAuth = window.sessionStorage.getItem("auth");
        if (sessionUser) {
            this.setState({
                user: sessionUser,
                authenticated: sessionAuth,
            });
        }
    }

    render() {
        const saveLoginState = (email) => {
            this.setState({
                user: email,
                authenticated: true,
            });
            window.sessionStorage.setItem("user", email);
            window.sessionStorage.setItem("auth", true);
        };
        const logout = () => {
            this.setState({
                user: null,
                authenticated: false,
            });
            window.sessionStorage.clear();
        };
        const handlePolling = () => {
            this.setState({
                isPolling: !this.state.isPolling,
            });
        };
        return (
            <MyRouter
                authenticated={this.state.authenticated}
                saveLoginState={saveLoginState}
                logout={logout}
                isPolling={this.state.isPolling}
                handlePolling={handlePolling}
            />
        );
    }
}
const ApolloApp = () => (
    <ApolloProvider client={client}>
        <App />
    </ApolloProvider>
);
export default ApolloApp;
