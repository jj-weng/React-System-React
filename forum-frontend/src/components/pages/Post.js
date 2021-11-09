import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Button from "@mui/material/Button";
import CameraIcon from "@mui/icons-material/PhotoCamera";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import CssBaseline from "@mui/material/CssBaseline";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
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
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Link } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import FeedIcon from "@mui/icons-material/Feed";
import ThreadContext from "../../context/thread/threadContext";
import UserContext from "../../context/user/userContext";
import { useState, useContext, useEffect } from "react";
import { useLocation } from "react-router-dom";

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

export const Post = (props) => {
  const handleClick = (event) => {
    event.preventDefault();
    setOpen(false);
    console.log(post);
    createPost(post, user.id, id);
    // eslint-disable-next-line no-console
  };

  const userContext = useContext(UserContext);
  const { user, isLogin } = userContext;

  const threadContext = useContext(ThreadContext);
  const { getThread, posts, createPost, thread, getForum, forum } = threadContext;

  const location = useLocation();
  const { id } = location.state;

  // const store = id;

  const [post, setPost] = useState({
    body: "",
    title: "",
  });

  const { body, title } = post;

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = (event) => {
    setOpen(true);
  };
  const handleClose = (event) => {
    setOpen(false);
  };

  const onChange = (e) => setPost({ ...post, [e.target.name]: e.target.value });

  useEffect(() => {
    getThread(id);
    getForum(thread.id);
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
              <FeedIcon fontSize="large" />
              Posts
            </Typography>
            <div>
              {thread.threadOpenClose === "OPENED" && (
                <Button variant="outlined" onClick={handleClickOpen}>
                  <AddCircleIcon /> Add Post
                </Button>
              )}
              <BootstrapDialog
                onClose={handleClose}
                aria-labelledby="customized-dialog-title"
                open={open}
              >
                <BootstrapDialogTitle
                  id="customized-dialog-title"
                  onClose={handleClose}
                >
                  New Post
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
                    Fill in the title and body of the post you would like to
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
            {posts !== null &&
              posts.length !== 0 &&
              posts.map((post) => (
                <Grid item key={post.id} post={post} xs={12} sm={6} md={12}>
                  <Card
                    sx={{
                      height: "150%",
                      display: "flex",
                      flexDirection: "column",
                    }}
                  >
                    <CardContent sx={{ flexGrow: 1 }}>
                      <Typography gutterBottom variant="h5" component="h2">
                        {post.title}
                      </Typography>
                      <Typography>{post.body}</Typography>
                    </CardContent>
                    <CardActions>
                      {isLogin &&
                        (user.email === "admin@gmail.com" ||
                          user.email === post.user.email) && (
                          <Link
                            to={{
                              pathname: `/${post.id}/editPost`,
                              state: {
                                id: post.id,
                              },
                            }}
                          >
                            <Button size="small">
                              {" "}
                              <EditIcon /> Edit Post
                            </Button>
                          </Link>
                        )}
                      {isLogin &&
                        (user.email === "admin@gmail.com" ||
                          user.email === post.user.email) && (
                          <Link
                            to={{
                              pathname: `/${post.id}/deletePost`,
                              state: {
                                id: post.id,
                              },
                            }}
                          >
                            <Button size="small">
                              {" "}
                              <DeleteIcon /> Delete Post
                            </Button>
                          </Link>
                        )}
                    </CardActions>
                  </Card>
                </Grid>
              ))}
          </Grid>
        </Container>
        {console.log(forum.id)}
        <Container sx={{ py: 5 }} maxWidth="md">
          <div>
            <Link
              to={{
                pathname: `/${forum.id}/threads`,
                state: {
                  id: forum.id,
                },
              }}
            >
              <Button variant="outlined">
                <ArrowBackIcon />
                Back to Threads
              </Button>
            </Link>
          </div>
        </Container>
      </main>
      {/* Footer */}
    </ThemeProvider>
  );
};
