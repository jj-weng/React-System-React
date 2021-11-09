import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import DeleteIcon from "@mui/icons-material/Delete";
import AppRegistrationOutlinedIcon from "@mui/icons-material/AppRegistrationOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link } from "react-router-dom";
import ForumContext from "../../context/forum/forumContext";
import { useLocation } from "react-router-dom";
import { useState, useContext, useEffect } from "react";

const theme = createTheme();

export const DeleteForum = (props) => {
  const location = useLocation();
  const { id } = location.state;
  const forumContext = useContext(ForumContext);
  const { getForum, forum, deleteForum } = forumContext;

  //   const [tempForum, setForum] = useState({
  //     id: id,
  //     title: forum.title,
  //     description: forum.description,
  //     body: forum.body,
  //   });

  //   const { title, description, body } = tempForum;

  useEffect(() => {
    getForum(id);
  }, []);

  //   const onChange = (e) =>
  //     setForum({ ...tempForum, [e.target.name]: e.target.value });

  const onSubmit = (event) => {
    event.preventDefault();
    console.log(forum);
    deleteForum(forum);
    alert("Forum successfully deleted.");
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <DeleteIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Delete Forum
          </Typography>
          <Box component="form" noValidate onSubmit={onSubmit} sx={{ mt: 3 }}>
            {forum != {} && (
              <Grid container spacing={2}>
                {/* <Grid item xs={12}>
                  <label for="title"> Title </label>
                  <TextField
                    name="title"
                    defaultValue={forum.title}
                    disabled
                    fullWidth
                  />
                </Grid>
                <Grid item xs={12}>
                  <label for="description"> Description </label>
                  <TextField
                    fullWidth
                    disabled
                    defaultValue={forum.description}
                    // value={description}
                    name="description"
                  />
                </Grid>
                <Grid item xs={12}>
                  <label for="body"> Body </label>
                  <TextField
                    disabled
                    fullWidth
                    name="body"
                    defaultValue={forum.body}
                  />
                </Grid> */}
              </Grid>
            )}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Confirm Delete
            </Button>
          </Box>
          <Box>
            <Link
              to={{
                pathname: "/forum",
              }}
            >
              <Button fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Back
              </Button>
            </Link>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default DeleteForum;
