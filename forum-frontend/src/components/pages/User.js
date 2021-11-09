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
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import BlockIcon from "@mui/icons-material/Block";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Link } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import UserContext from "../../context/user/userContext";
import { useState, useContext, useEffect } from "react";

const cards = [1, 2, 3, 4, 5, 6, 7, 8, 9];

const theme = createTheme();

export const User = (props) => {
  const userContext = useContext(UserContext);
  const { getUsers, users, blockUser, unblockUser } = userContext;

  // const [tempUser, setUser] = useState({
  //   id: user.id,
  //   name: user.name,
  //   email: user.email,
  //   password: user.password,
  //   gender: user.gender,
  //   userStatus: user.userStatus,
  // });

  // const { userStatus } = tempUser;

  useEffect(() => {
    getUsers();
  }, []);

  // const onChange = (e) =>
  //   setUser({ ...tempUser, [e.target.name]: e.target.value });

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
              <PeopleAltIcon fontSize="large" />
              Users
            </Typography>
          </Container>
        </Box>
        <Container sx={{ pb: 8 }} maxWidth="md">
          {/* End hero unit */}
          <Grid container spacing={4}>
            {users.map((user) => {
              const handleBlock = (e) => {
                e.preventDefault();
                blockUser(user);
                alert("User successfully blocked.")
              };

              const handleUnblock = (e) => {
                e.preventDefault();
                unblockUser(user);
                alert("User successfully unblocked.")
              };
              return (
                <Grid item key={user.id} user={user} xs={12} sm={6} md={4}>
                  <Card
                    sx={{
                      height: "100%",
                      display: "flex",
                      flexDirection: "column",
                    }}
                  >
                    <CardContent sx={{ flexGrow: 1 }}>
                      <Typography gutterBottom variant="h5" component="h2">
                        Name: {user.name}
                      </Typography>
                      <Typography>Email: {user.email}</Typography>
                      <Typography> Gender: {user.gender}</Typography>
                      <Typography> Status: {user.userStatus}</Typography>
                    </CardContent>
                    <CardActions>
                      <Link>
                        <Button size="small" onClick={handleBlock}>
                          {" "}
                          <BlockIcon /> Block User
                        </Button>
                      </Link>
                      <Link>
                        <Button size="small" onClick={handleUnblock}>
                          {" "}
                          <CheckCircleOutlineIcon /> Unblock User
                        </Button>
                      </Link>
                    </CardActions>
                  </Card>
                </Grid>
              );
            })}
          </Grid>
        </Container>
      </main>
      {/* Footer */}
    </ThemeProvider>
  );
};
