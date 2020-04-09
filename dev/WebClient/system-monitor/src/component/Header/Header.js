import React  from 'react';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import CssBaseline from '@material-ui/core/CssBaseline';
import useScrollTrigger from '@material-ui/core/useScrollTrigger';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import Slide from '@material-ui/core/Slide';
import Index from '../Index/Index';
function HideOnScroll(props) {
    const { children, window } = props;
    // Note that you normally won't need to set the window ref as useScrollTrigger
    // will default to window.
    // This is only being set here because the demo is in an iframe.
    const trigger = useScrollTrigger({ target: window ? window() : undefined });
  
    return (
      <Slide appear={false} direction="down" in={!trigger}>
        {children}
      </Slide>
    );
  }
  
  HideOnScroll.propTypes = {
    children: PropTypes.element.isRequired,
    /**
     * Injected by the documentation to work in an iframe.
     * You won't need it on your project.
     */
    window: PropTypes.func,
  };
  
const Header = (props) => {
    const style={
        backgroundColor:"black",
        height:"50px",
        opacity:"80%"
    }
    const {isPolling,handlePolling} = props;

    return (
        <React.Fragment>
          <CssBaseline />
          <HideOnScroll {...props}>
            <AppBar style={style}>
              <Toolbar >
                <Index
                 isPolling={isPolling} handlePolling={handlePolling}
                ></Index>
              </Toolbar>
            </AppBar>
          </HideOnScroll>
        </React.Fragment>
      );
}

export default Header;