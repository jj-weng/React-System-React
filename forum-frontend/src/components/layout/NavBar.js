import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import ClickAwayListener from "@mui/material/ClickAwayListener";
import Grow from "@mui/material/Grow";
import Paper from "@mui/material/Paper";
import Popper from "@mui/material/Popper";
import MenuItem from "@mui/material/MenuItem";
import MenuList from "@mui/material/MenuList";
import Stack from "@mui/material/Stack";
import { Link } from "react-router-dom";
import UserContext from "../../context/user/userContext";
import { useState, useContext, useEffect } from "react";

const NavBar = () => {
  const userContext = useContext(UserContext);
  const { logout, user, isLogin, refresh } = userContext;

  useEffect(() => {
    refresh();
  }, []);

  const [open, setOpen] = React.useState(false);
  const anchorRef = React.useRef(null);

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event) => {
    if (anchorRef.current && anchorRef.current.contains(event.target)) {
      return;
    }
    setOpen(false);
  };

  const handleLogout = (event) => {
    if (anchorRef.current && anchorRef.current.contains(event.target)) {
      return;
    }
    setOpen(false);
    logout();
  };

  function handleListKeyDown(event) {
    if (event.key === "Tab") {
      event.preventDefault();
      setOpen(false);
    } else if (event.key === "Escape") {
      setOpen(false);
    }
  }

  // return focus to the button when we transitioned from !open -> open
  const prevOpen = React.useRef(open);
  React.useEffect(() => {
    if (prevOpen.current === true && open === false) {
      anchorRef.current.focus();
    }

    prevOpen.current = open;
  }, [open]);

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <Stack direction="row" spacing={2}>
              <div>
                <Button
                  ref={anchorRef}
                  id="composition-button"
                  aria-controls={open ? "composition-menu" : undefined}
                  aria-expanded={open ? "true" : undefined}
                  aria-haspopup="true"
                  color="inherit"
                  onClick={handleToggle}
                >
                  {isLogin && <MenuIcon />}
                </Button>
                <Popper
                  open={open}
                  anchorEl={anchorRef.current}
                  role={undefined}
                  placement="bottom-start"
                  transition
                  disablePortal
                >
                  {({ TransitionProps, placement }) => (
                    <Grow
                      {...TransitionProps}
                      style={{
                        transformOrigin:
                          placement === "bottom-start"
                            ? "left top"
                            : "left bottom",
                      }}
                    >
                      <Paper>
                        <ClickAwayListener onClickAway={handleClose}>
                          <MenuList
                            autoFocusItem={open}
                            id="composition-menu"
                            aria-labelledby="composition-button"
                            onKeyDown={handleListKeyDown}
                          >
                            {isLogin && (
                              <MenuItem onClick={handleClose}>
                                <Link
                                  to="/profile"
                                  style={{ color: "#000000" }}
                                >
                                  My Profile
                                </Link>
                              </MenuItem>
                            )}
                            {isLogin && (
                              <MenuItem onClick={handleClose}>
                                <Link
                                  to="/editProfile"
                                  style={{ color: "#000000" }}
                                >
                                  Edit My Profile
                                </Link>
                              </MenuItem>
                            )}
                            {isLogin && (
                              <MenuItem onClick={handleClose}>
                                <Link to="/forum" style={{ color: "#000000" }}>
                                  View All Forums
                                </Link>
                              </MenuItem>
                            )}
                            {isLogin && user.email === "admin@gmail.com" && (
                              <MenuItem onClick={handleClose}>
                                <Link to="/user" style={{ color: "#000000" }}>
                                  View All Users
                                </Link>
                              </MenuItem>
                            )}
                            {isLogin && (
                              <MenuItem onClick={handleLogout}>
                                <Link to="/login" style={{ color: "#000000" }}>
                                  Logout
                                </Link>
                              </MenuItem>
                            )}
                          </MenuList>
                        </ClickAwayListener>
                      </Paper>
                    </Grow>
                  )}
                </Popper>
              </div>
            </Stack>
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Welcome!
          </Typography>
          {!isLogin && (
            <Button>
              <Link to="/login" style={{ color: "#FFF" }}>
                Login
              </Link>
            </Button>
          )}
          {!isLogin && (
            <Button>
              <Link to="/register" style={{ color: "#FFF" }}>
                Register
              </Link>
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default NavBar;
