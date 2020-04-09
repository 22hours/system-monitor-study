import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import './Index.css';
import Switch from '@material-ui/core/Switch';
import FormControlLabel from '@material-ui/core/FormControlLabel';
const Index = ({isPolling,handlePolling}) => {
 
    const [check,setCheck] = useState(isPolling);
    useEffect(()=>{
        setCheck(isPolling);
    },[isPolling])
    return (
        <React.Fragment>
            <div className="header-logo-box">
                <img src="/img/image_logo.png"></img>
            </div>
            <div className="header-link-box">
                <Link to="/total">TOTAL</Link>
            </div>
            <div className="header-link-box">
                <Link to="/two">CLASS</Link>
            </div>
            <div>
            <FormControlLabel
            checked={check}
            onChange={handlePolling}
            control={<Switch color="primary" />}
            label="POLLING"
            labelPlacement="start"
        />
            </div>
        </React.Fragment>
    );
}

export default Index;