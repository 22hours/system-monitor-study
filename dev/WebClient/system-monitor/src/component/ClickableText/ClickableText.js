import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
// const useStyles = makeStyles((theme) => ({
//     root: {
//       '& > *': {
//         margin: theme.spacing(1),
//       },
//     },
//   }));
  
const smallButtonStyle ={
    height:"30px",
};
const ClickableText = ({text,handlePolling}) =>{
    return(
        <React.Fragment>
            <Button onClick={handlePolling} style={smallButtonStyle} size="small" color="primary">{text}</Button>
        </React.Fragment>
    );
}

export default ClickableText;