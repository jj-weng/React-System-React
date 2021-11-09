import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined";
import AppRegistrationOutlinedIcon from "@mui/icons-material/AppRegistrationOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import UserContext from "../../context/user/userContext";
import { useState, useContext, useEffect } from "react";
import { Refresh } from "@mui/icons-material";

const theme = createTheme();

export const EditProfile = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    // eslint-disable-next-line no-console
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
    editUser(tempUser);
    alert("Profile successfully edited.");
  };

  const userContext = useContext(UserContext);
  const { editUser, user, refresh } = userContext;

  const [tempUser, setUser] = useState({
    id: user.id,
    name: user.name,
    email: user.email,
    password: user.password,
    gender: user.gender,
    userStatus: user.userStatus,
  });

  useEffect(() => {
    // refresh();
  }, []);

  const { name, email, password, gender } = tempUser;

  const onChange = (e) =>
    setUser({ ...tempUser, [e.target.name]: e.target.value });

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
            <AccountCircleOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Edit Profile
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <label for="name"> Name </label>
                <TextField
                  //   autoComplete="name"
                  name="name"
                  required
                  fullWidth
                  value={name}
                  autoFocus
                  onChange={onChange}
                />
              </Grid>
              <Grid item xs={12}>
                <label for="email"> Email </label>
                <TextField
                  fullWidth
                  name="email"
                  value={email}
                  autoFocus
                  onChange={onChange}
                  //   autoComplete="family-name"
                />
              </Grid>
              <Grid item xs={12}>
                <label for="Password"> Password </label>
                <TextField
                  fullWidth
                  name="password"
                  value={password}
                  autoFocus
                  onChange={onChange}
                />
              </Grid>
              <Grid item xs={12}>
                <label for="gender"> Gender </label>
                <TextField
                  fullWidth
                  name="gender"
                  value={gender}
                  autoFocus
                  onChange={onChange}
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Save Changes
            </Button>
          </Box>
        </Box>
      </Container>
      <Container sx={{ py: 6 }} maxWidth="md"></Container>
    </ThemeProvider>
  );
};

export default EditProfile;
