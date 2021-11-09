import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Button from "@mui/material/Button";
import CameraIcon from "@mui/icons-material/PhotoCamera";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import VisibilityIcon from "@mui/icons-material/Visibility";
import LockIcon from "@mui/icons-material/Lock";
import LockOpenIcon from "@mui/icons-material/LockOpen";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import CssBaseline from "@mui/material/CssBaseline";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import TextField from "@mui/material/TextField";
import DialogTitle from "@mui/material/DialogTitle";
import { styled } from "@mui/material/styles";
import PropTypes from "prop-types";
import DialogContent from "@mui/material/DialogContent";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Link } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import ListAltIcon from "@mui/icons-material/ListAlt";
import { useLocation } from "react-router-dom";
import UserContext from "../../context/user/userContext";
import ForumContext from "../../context/forum/forumContext";
import { useState, useContext, useEffect } from "react";

const cards = [1, 2, 3, 4, 5, 6, 7, 8, 9];

const theme = createTheme();

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
  "& .MuiDialogContent-root": {
    padding: theme.spacing(2),
  },
  "& .MuiDialogActions-root": {
    padding: theme.spacing(1),
  },
}));

const BootstrapDialogTitle = (props) => {
  const { children, onClose, ...other } = props;

  return (
    <DialogTitle sx={{ m: 0, p: 2 }} {...other}>
      {children}
      {onClose ? (
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={{
            position: "absolute",
            right: 8,
            top: 8,
            color: (theme) => theme.palette.grey[500],
          }}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </DialogTitle>
  );
};

BootstrapDialogTitle.propTypes = {
  children: PropTypes.node,
  onClose: PropTypes.func.isRequired,
};

export const Thread = (props) => {
  const handleClick = (event) => {
    event.preventDefault();
    setOpen(false);
    console.log(thread);
    createThread(thread, user.id, id);
    // eslint-disable-next-line no-console
  };

  const userContext = useContext(UserContext);
  const { user, isLogin } = userContext;

  const forumContext = useContext(ForumContext);
  const { getForum, threads, closeThread, openThread, createThread } =
    forumContext;

  const location = useLocation();
  const { id } = location.state;

  const [thread, setThread] = useState({
    body: "",
    posts: [],
    title: "",
    threadOpenClose: "OPENED",
  });

  const { body, title } = thread;

  const store = id;

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = (event) => {
    setOpen(true);
  };
  const handleClose = (event) => {
    setOpen(false);
  };

  const onChange = (e) =>
    setThread({ ...thread, [e.target.name]: e.target.value });

  useEffect(() => {
    getForum(id);
  }, []);

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <main>
        {/* Hero unit */}
        <Box
          sx={{
            bgcolor: "background.paper",
            pt: 8,
            pb: 6,
          }}
        >
          <Container maxWidth="sm">
            <Typography
              component="h1"
              variant="h2"
              align="center"
              color="text.primary"
              gutterBottom
            >
              <ListAltIcon fontSize="large" />
              Threads
            </Typography>
            <div>
              <Button variant="outlined" onClick={handleClickOpen}>
                <AddCircleIcon /> Add Thread
              </Button>
              <BootstrapDialog
                onClose={handleClose}
                aria-labelledby="customized-dialog-title"
                open={open}
              >
                <BootstrapDialogTitle
                  id="customized-dialog-title"
                  onClose={handleClose}
                >
                  New Thread
                </BootstrapDialogTitle>
                <DialogContent dividers>
                  <Grid item xs={12}>
                    <TextField
                      name="title"
                      value={title}
                      required
                      fullWidth
                      multiline
                      maxRows={4}
                      id="Title"
                      label="Title"
                      autoFocus
                      onChange={onChange}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      name="body"
                      value={body}
                      required
                      multiline
                      maxRows={8}
                      fullWidth
                      id="Body"
                      label="Body"
                      autoFocus
                      onChange={onChange}
                    />
                  </Grid>
                  <Typography gutterBottom>
                    Fill in the title and body of the thread you would like to
                    create!
                  </Typography>
                </DialogContent>
                <DialogActions>
                  <Button autoFocus onClick={handleClick}>
                    Confirm
                  </Button>
                </DialogActions>
              </BootstrapDialog>
            </div>
          </Container>
        </Box>
        <Container sx={{ pb: 8 }} maxWidth="md">
          {/* End hero unit */}
          <Grid container spacing={14}>
            {threads.map((thread) => {
              const handleClose = (e) => {
                e.preventDefault();
                closeThread(thread);
                alert("Thread successfully closed.");
              };

              const handleOpen = (e) => {
                e.preventDefault();
                openThread(thread);
                alert("Thread successfully opened.");
              };
              return (
                <Grid
                  item
                  key={thread.id}
                  thread={thread}
                  xs={12}
                  sm={6}
                  md={12}
                >
                  <Card
                    sx={{
                      height: "150%",
                      display: "flex",
                      flexDirection: "column",
                    }}
                  >
                    <CardContent sx={{ flexGrow: 1 }}>
                      <Typography align="right">
                        {" "}
                        Thread is {thread.threadOpenClose}
                      </Typography>
                      <Typography gutterBottom variant="h5" component="h2">
                        {thread.title}
                      </Typography>
                      <Typography>{thread.body}</Typography>
                    </CardContent>
                    <CardActions>
                      <Link
                        to={{
                          pathname: `/${thread.id}/posts`,
                          state: {
                            id: thread.id,
                          },
                        }}
                      >
                        {" "}
                        <Button size="small">
                          <VisibilityIcon /> View Posts{" "}
                        </Button>
                      </Link>
                      {isLogin &&
                        (user.email === "admin@gmail.com" ||
                          user.email === thread.userEntity.email) && (
                          <Link
                            to={{
                              pathname: `/${thread.id}/editThread`,
                              state: {
                                id: thread.id,
                              },
                            }}
                          >
                            <Button size="small">
                              {" "}
                              <EditIcon />
                              Edit Thread
                            </Button>
                          </Link>
                        )}
                      {isLogin &&
                        (user.email === "admin@gmail.com" ||
                          user.email === thread.userEntity.email) && (
                          <Link
                            to={{
                              pathname: `/${thread.id}/deleteThread`,
                              state: {
                                id: thread.id,
                              },
                            }}
                          >
                            <Button size="small">
                              {" "}
                              <DeleteIcon />
                              Delete Thread
                            </Button>
                          </Link>
                        )}
                      {isLogin && user.email === "admin@gmail.com" && (
                        <Link
                          to={{
                            pathname: `/${store}/threads`,
                            state: {
                              id: store,
                            },
                          }}
                        >
                          <Button size="small" onClick={handleClose}>
                            {" "}
                            <LockIcon />
                            Close Thread
                          </Button>
                        </Link>
                      )}
                      {isLogin && user.email === "admin@gmail.com" && (
                        <Link
                          to={{
                            pathname: `/${store}/threads`,
                            state: {
                              id: store,
                            },
                          }}
                        >
                          <Button size="small" onClick={handleOpen}>
                            {" "}
                            <LockOpenIcon />
                            Open Thread
                          </Button>
                        </Link>
                      )}
                    </CardActions>
                  </Card>
                </Grid>
              );
            })}
          </Grid>
        </Container>
        <Container sx={{ py: 8 }} maxWidth="md">
          <div>
            <Link
              to={{
                pathname: "/forum",
              }}
            >
              <Button variant="outlined">
                <ArrowBackIcon />
                Back to Forums
              </Button>
            </Link>
          </div>
        </Container>
      </main>
      {/* Footer */}
    </ThemeProvider>
  );
};
