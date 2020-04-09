import React, { useState } from "react";
import { Container } from "reactstrap";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import axios from "axios";
import { Link, Redirect } from "react-router-dom";
import "./Login.css";
const Login = ({ saveLoginState, authenticated, location }) => {
    const [id, setId] = useState("");
    const [pw, setPw] = useState("");
    const { from } = location.state || { from: { pathname: "/" } };
    if (authenticated) return <Redirect to={from} />;

    return (
        <Container>
            <div className="login-wrapper">
                <Container>
                    <div className="header-box">
                        <p className="header">SYSTEM MONITOR</p>
                        <p className="copylight">Developed By 22HOURS</p>
                    </div>
                    <div className="input-box">
                        <div className="id-box">
                            <div className="input-box-sizing">
                                <TextField
                                    value={id}
                                    onChange={({ target: { value } }) => setId(value)}
                                    size="small"
                                    id="outlined-basic"
                                    label="ID"
                                    variant="outlined"
                                />
                            </div>
                        </div>
                        <div className="pw-box">
                            <div className="input-box-sizing">
                                <TextField
                                    value={pw}
                                    onChange={({ target: { value } }) => setPw(value)}
                                    size="small"
                                    id="outlined-basic"
                                    label="PW"
                                    variant="outlined"
                                />
                            </div>
                        </div>
                    </div>
                    <div className="submit-box">
                        <div className="button-box">
                            <Button
                                onClick={() => {
                                    if (id.length < 1) return;
                                    if (pw.length < 1) return;
                                    saveLoginState(id);
                                }}
                                variant="contained"
                                color="primary"
                            >
                                Login
                            </Button>
                        </div>
                        <div className="help-box">
                            <Link>비밀번호를 잊으셨나요?</Link>
                        </div>
                    </div>
                </Container>
            </div>
        </Container>
    );
};

export default Login;
