import React, { Component } from "react";
import "./PcItem.css";
import { Spinner, Alert, Progress, Collapse, Button, CardBody, Card } from "reactstrap";
import axios from "axios";
import { getFilteredDate, plus30minute } from "../../util/time";
class PcItem extends Component {
    //({handleOffPc,handleDelayPc, id, powerStatus, ramData, cpuData, endTime })

    constructor(props) {
        super(props);
        var currentDate = new Date();
        var now = currentDate.getHours() + "시";
        now += currentDate.getMinutes() + "분";
        now += currentDate.getSeconds() + "초";
        this.state = {
            ...props,
            isOpen: false,
            nowOffButtonRunning: false,
            nowDelayButtonRunning: false,
            updateTime: now,
        };
    }

    componentWillReceiveProps(nextProps, prevState) {
        var currentDate = new Date();
        var now = currentDate.getHours() + "시";
        now += currentDate.getMinutes() + "분";
        now += currentDate.getSeconds() + "초";
        var nextIsOpen = false;
        console.log(this.state.isOpen);
        if (this.state.isOpen) {
            nextIsOpen = true;
        }
        this.setState({
            ...nextProps,
            isOpen: nextIsOpen,
            nowOffButtonRunning: false,
            nowDelayButtonRunning: false,
            updateTime: now,
        });
    }

    render() {
        const {
            id,
            powerStatus,
            ramData,
            cpuData,
            startTime,
            endTime,
            isOpen,
            nowOffButtonRunning,
            nowDelayButtonRunning,
            updateTime,
        } = this.state;
        console.log("render This!" + id);
        const Offlight = () => {
            return <div className="off-light"></div>;
        };
        const Onlight = () => {
            return <div className="on-light"></div>;
        };
        const Powerlight = () => {
            if (powerStatus === "ON" || powerStatus === "On") {
                return <Onlight></Onlight>;
            } else return <Offlight></Offlight>;
        };

        const toggle = () => {
            this.setState({
                isOpen: !isOpen,
            });
        };

        const pcOffEvent = () => {
            this.setState({
                nowOffButtonRunning: true,
            });
            let today = new Date();
            var sendTime = getFilteredDate(today);
            axios
                .post("/mobile/pc/" + id + "/power/" + sendTime, {
                    params: {
                        endTime: sendTime,
                        powerStatus: "OFF",
                    },
                })
                .then(() => {
                    reload();
                })
                .catch((error) => {
                    postError();
                });
        };

        const pcDelay = () => {
            this.setState({
                nowDelayButtonRunning: true,
            });
            let today = new Date();
            var sendTime = getFilteredDate(plus30minute());
            axios
                .post("/mobile/pc/" + id + "/power/" + sendTime, {
                    params: {
                        endTime: sendTime,
                    },
                })
                .then(() => {
                    reload();
                })
                .catch((error) => {
                    postError();
                });
        };

        const postError = () => {
            this.setState({
                nowOffButtonRunning: false,
                nowDelayButtonRunning: false,
            });
        };

        const reload = () => {
            var currentDate = new Date();
            var now = currentDate.getHours() + "시";
            now += currentDate.getMinutes() + "분";
            now += currentDate.getSeconds() + "초";
            axios
                .get("pc/" + id)
                .then((response) => {
                    console.log("Reload Success!");
                    console.log(response);
                    this.setState({
                        ...response.data,
                        nowOffButtonRunning: false,
                        nowDelayButtonRunning: false,
                        updateTime: now,
                    });
                })
                .catch(function (error) {
                    console.log(error);
                });
        };

        const OffButtonState = () => {
            if (nowOffButtonRunning) {
                return (
                    <div>
                        현재 서버와 통신중입니다....
                        <Spinner size="sm" color="secondary" />
                    </div>
                );
            } else {
                return (
                    <Button color="danger" id="offButton" onClick={() => pcOffEvent(id)}>
                        종료하기
                    </Button>
                );
            }
        };

        const DelayButtonState = () => {
            if (nowDelayButtonRunning) {
                return (
                    <div>
                        현재 서버와 통신중입니다....
                        <Spinner size="sm" color="secondary" />
                    </div>
                );
            } else {
                return (
                    <Button color="primary" id="offButton" onClick={() => pcDelay(id)}>
                        30분 연장하기
                    </Button>
                );
            }
        };

        const ButtonState = () => {
            if (nowDelayButtonRunning || nowOffButtonRunning) {
                return (
                    <div>
                        현재 서버와 통신중입니다....
                        <Spinner size="sm" color="secondary" />
                    </div>
                );
            } else {
                return (
                    <React.Fragment>
                        <div className="collapse-item-wrapper">
                            <DelayButtonState />
                        </div>
                        <div className="collapse-item-wrapper">
                            <OffButtonState />
                        </div>
                    </React.Fragment>
                );
            }
        };

        const GetRamNotice = () => {
            if (ramData >= 90) {
                return (
                    <div className="usage-over-90">
                        <p>PC가 뜨겁습니다!</p>
                    </div>
                );
            } else if (ramData >= 70) {
                return (
                    <div className="usage-over-70">
                        <p>누군가가 열심히 작업하고 있습니다</p>
                    </div>
                );
            } else if (ramData >= 50) {
                return (
                    <div className="usage-over-50">
                        <p>과제를 하고있나요?</p>
                    </div>
                );
            } else if (ramData >= 30) {
                return (
                    <div className="usage-over-30">
                        <p>그렇게 많은 전기세는 안나오겠군요</p>
                    </div>
                );
            } else if (ramData >= 10) {
                return (
                    <div className="usage-over-10">
                        <p>쓰고있는건가요?</p>
                    </div>
                );
            } else {
                return (
                    <div className="usage-over-0">
                        <p>정보가 없네요</p>
                    </div>
                );
            }
        };

        const GetCpuNotice = () => {
            if (cpuData >= 90) {
                return (
                    <div className="usage-over-90">
                        <p>PC가 뜨겁습니다!</p>
                    </div>
                );
            } else if (cpuData >= 70) {
                return (
                    <div className="usage-over-70">
                        <p>누군가가 열심히 작업하고 있습니다</p>
                    </div>
                );
            } else if (cpuData >= 50) {
                return (
                    <div className="usage-over-50">
                        <p>과제를 하고있나요?</p>
                    </div>
                );
            } else if (cpuData >= 30) {
                return (
                    <div className="usage-over-30">
                        <p>그렇게 많은 전기세는 안나오겠군요</p>
                    </div>
                );
            } else if (cpuData >= 10) {
                return (
                    <div className="usage-over-10">
                        <p>쓰고있는건가요?</p>
                    </div>
                );
            } else {
                return (
                    <div className="usage-over-0">
                        <p>정보가 없네요</p>
                    </div>
                );
            }
        };

        const getFilteredTime = (time) => {
            const nowTime = new String(time);
            const nowSplitedTime = nowTime.split("-");
            if (nowSplitedTime.length < 4) return "정보가 없습니다";
            return (
                nowSplitedTime[0] +
                "년 " +
                nowSplitedTime[1] +
                "월 " +
                nowSplitedTime[2] +
                "일 " +
                nowSplitedTime[3] +
                "시" +
                nowSplitedTime[4] +
                "분 "
            );
        };

        const CollapseChild = () => {
            return (
                <div className="pc-detail-wrapper">
                    <div className="collapse-item-wrapper">
                        <div className="collapse-item-box">
                            <div className="info-text-box">
                                <p>RAM 실시간 사용량</p>
                            </div>
                            <div className="value-text-box">
                                <span>{ramData}%</span>
                            </div>
                            <div className="data-progress-box">
                                <Progress value={ramData} />
                            </div>
                            <div>
                                <GetRamNotice />
                            </div>
                        </div>
                    </div>

                    <div className="collapse-item-wrapper">
                        <div className="collapse-item-box">
                            <div className="info-text-box">
                                <p>CPU 실시간 사용량</p>
                            </div>
                            <div className="value-text-box">
                                <span>{cpuData}%</span>
                            </div>
                            <div className="data-progress-box">
                                <Progress value={cpuData} />
                            </div>
                            <div>
                                <GetCpuNotice />
                            </div>
                        </div>
                    </div>

                    <div className="collapse-item-wrapper">
                        <div className="collapse-item-box">
                            <div className="info-text-box">
                                <p>PC 사용 시작 시간</p>
                            </div>
                            <div className="value-text-box">
                                <span>{getFilteredTime(startTime)}</span>
                            </div>
                        </div>
                    </div>

                    <div className="collapse-item-wrapper">
                        <div className="collapse-item-box">
                            <div className="info-text-box">
                                <p>PC 사용 종료 시간</p>
                            </div>
                            <div className="value-text-box">
                                <span>{getFilteredTime(endTime)}</span>
                            </div>
                        </div>
                    </div>
                    <ButtonState />
                    <div className="collapse-item-wrapper">
                        <div className="collapse-item-box">
                            <div className="info-text-box">
                                <p>현재 서버와의 연결 로그</p>
                            </div>
                            <div className="value-text-box">
                                <span>{getFilteredTime(endTime)}</span>
                            </div>
                        </div>
                    </div>
                </div>
            );
            if (powerStatus === "ON" || powerStatus === "On") {
                return (
                    <Card>
                        <CardBody>
                            <p>RAM : {ramData}</p>
                            <Progress value={ramData} />

                            <p>CPU : {cpuData}</p>
                            <Progress value={cpuData} />
                            <Button onClick={pcOffEvent(id)}>>OFF</Button>
                            <Button onClick={toggle}>-</Button>
                        </CardBody>
                    </Card>
                );
            } else {
                return (
                    <div className="pc-off-alert-box">
                        <Alert color="dark">이 PC는 종료되었습니다.</Alert>
                    </div>
                );
            }
        };

        return (
            <React.Fragment>
                <div className="PcItem">
                    <div className="pc-item-wrapper">
                        <Powerlight />
                        <div className="pc-item-box">
                            <div onClick={toggle} className="id-box">
                                <p>
                                    <span className="id-span">{id}</span>
                                    <span className="update-log">UPDATED : {updateTime}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <Collapse isOpen={isOpen}>
                        <CollapseChild />
                    </Collapse>
                </div>
            </React.Fragment>
        );
    }
}

export default PcItem;
