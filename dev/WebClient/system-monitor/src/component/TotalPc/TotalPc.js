import React, { useState, useEffect } from "react";
import axios from "axios";
import PcItem from "../PcItem/PcItem";
import ScrollButton from "../ScrollButton/ScrollButton";
import { Container, Spinner, Alert } from "reactstrap";
import "./TotalPc.css";
import ClickableText from "../ClickableText/ClickableText";
const smallPaddingStyle = {
    padding: "5px",
};

const TotalPc = ({ isPolling, handlePolling }) => {
    const [pcs, setPcs] = useState(null);
    const [onPcs, setOnPcs] = useState(null);
    const [offPcs, setOffPcs] = useState(null);

    const getPcs = () => {
        axios
            .get("mobile/pc")
            .then((response) => {
                // console.log(response);
                // console.log(response.data.pcs);
                var sortingField = "powerStatus";
                const sortedResponse = response.data.pcs.sort((a, b) => {
                    return a[sortingField] - b[sortingField];
                });
                const filterOn = sortedResponse.filter((it) => it.powerStatus === "ON" || it.powerStatus === "On");
                setOnPcs(
                    filterOn.map(({ powerStatus, ramData, cpuData, startTime, endTime, id }) => (
                        <PcItem
                            key={id}
                            id={id}
                            powerStatus={powerStatus}
                            ramData={ramData}
                            cpuData={cpuData}
                            endTime={endTime}
                        />
                    ))
                );
                const filterOff = sortedResponse.filter((it) => it.powerStatus === "OFF" || it.powerStatus === "Off");
                setOffPcs(
                    filterOff.map(({ powerStatus, ramData, cpuData, startTime, endTime, id }) => (
                        <PcItem
                            key={id}
                            id={id}
                            powerStatus={powerStatus}
                            ramData={ramData}
                            cpuData={cpuData}
                            endTime={endTime}
                        />
                    ))
                );
                setPcs(
                    sortedResponse.map(({ powerStatus, ramData, cpuData, startTime, endTime, id }) => (
                        <PcItem
                            key={id}
                            id={id}
                            powerStatus={powerStatus}
                            ramData={ramData}
                            cpuData={cpuData}
                            endTime={endTime}
                        />
                    ))
                );
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    useEffect(() => {
        console.log("Total Pc Render!");
    });

    useEffect(() => {
        console.log("useEffect!");
        getPcs();
    }, [1]);

    useEffect(() => {
        if (isPolling) {
            const intervals = setInterval(() => {
                getPcs();
            }, 30000);
            return () => clearInterval(intervals);
        } else {
            console.log("Polling is stopped");
        }
    }, [isPolling]);

    const RenderPollingState = () => {
        if (isPolling) {
            return (
                <Alert style={smallPaddingStyle} color="success">
                    <div className="polling-state-wrapper">
                        <div className="text-box">실시간 업데이트 중입니다</div>
                        <div className="spinner-box">
                            <Spinner size="sm" color="secondary" />
                        </div>
                    </div>
                </Alert>
            );
        } else {
            return (
                <Alert style={smallPaddingStyle} color="secondary">
                    <div className="polling-state-wrapper">
                        <div className="text-box">실시간 업데이트가 중지되었습니다.</div>
                        <div className="spinner-box">
                            <ClickableText handlePolling={handlePolling} text={"다시켜기"} />
                        </div>
                    </div>
                </Alert>
            );
        }
    };

    return (
        <React.Fragment>
            <ScrollButton scrollStepInPx="50" delayInMs="16.66" />
            <Container>
                <div>
                    <RenderPollingState />
                </div>
                <PcItem
                    id={"testId"}
                    key={"testKey"}
                    powerStatus={"ON"}
                    ramData={"58.234"}
                    cpuData={"34"}
                    startTime={"2020-04-04-12-12"}
                    endTime={"2020-04-04-13-13"}
                />
            </Container>
            <Container>
                {onPcs}
                {offPcs}
            </Container>
        </React.Fragment>
    );
};

export default TotalPc;
