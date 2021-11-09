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
import VisibilityIcon from "@mui/icons-material/Visibility";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import PropTypes from "prop-types";
import { styled } from "@mui/material/styles";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import TextField from "@mui/material/TextField";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Link } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import ForumIcon from "@mui/icons-material/Forum";
import ForumContext from "../../context/forum/forumContext";
import UserContext from "../../context/user/userContext";
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

export const Forum = (props) => {
  const handleClick = (event) => {
    event.preventDefault();
    setOpen(false);
    console.log(forum);
    createForum(forum);
    // eslint-disable-next-line no-console
  };

  const [forum, setForum] = useState({
    body: "",
    description: "",
    threads: [],
    title: "",
  });

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = (event) => {
    setOpen(true);
  };
  const handleClose = (event) => {
    setOpen(false);
  };

  const forumContext = useContext(ForumContext);
  const { getForums, forums, createForum, editForum } = forumContext;

  const userContext = useContext(UserContext);
  const { user, isLogin } = userContext;

  useEffect(() => {
    getForums();
  }, []);

  const { body, description, title } = forum;

  const onChange = (e) =>
    setForum({ ...forum, [e.target.name]: e.target.value });

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
              <ForumIcon fontSize="large" />
              Forums
            </Typography>
            <div>
              {isLogin && user.email === "admin@gmail.com" && (
                <Button variant="outlined" onClick={handleClickOpen}>
                  <AddCircleIcon /> Add Forum
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
                  New forum
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
                      name="description"
                      value={description}
                      required
                      fullWidth
                      multiline
                      maxRows={4}
                      id="Description"
                      label="Description"
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
                    Fill in the title, description and body of the forum you
                    would like to create!
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
            {forums.map((forum) => (
              <Grid item key={forum.id} forum={forum} xs={12} sm={6} md={6}>
                <Card
                  sx={{
                    height: "150%",
                    display: "flex",
                    flexDirection: "column",
                  }}
                >
                  <CardContent sx={{ flexGrow: 1 }}>
                    <Typography gutterBottom variant="h5" component="h2">
                      {forum.title}
                    </Typography>
                    <Typography>{forum.body}</Typography>
                  </CardContent>
                  <CardActions>
                    <Link
                      to={{
                        pathname: `/${forum.id}/threads`,
                        state: {
                          id: forum.id,
                        },
                      }}
                    >
                      {" "}
                      <Button size="small">
                        {" "}
                        <VisibilityIcon /> View Threads
                      </Button>
                    </Link>
                    {isLogin && user.email === "admin@gmail.com" && (
                      <Link
                        to={{
                          pathname: `/${forum.id}/editForum`,
                          state: {
                            id: forum.id,
                          },
                        }}
                      >
                        <Button size="small">
                          {" "}
                          <EditIcon />
                          Edit Forum
                        </Button>
                      </Link>
                    )}
                    {isLogin && user.email === "admin@gmail.com" && (
                      <Link
                        to={{
                          pathname: `/${forum.id}/deleteForum`,
                          state: {
                            id: forum.id,
                          },
                        }}
                      >
                        <Button size="small">
                          {" "}
                          <DeleteIcon />
                          Delete Forum
                        </Button>
                      </Link>
                    )}
                  </CardActions>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Container>
      </main>
      {/* Footer */}
    </ThemeProvider>
  );
};
