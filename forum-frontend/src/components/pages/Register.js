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
import PasswordStrengthBar from "react-password-strength-bar";
import validator from "validator";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import AppRegistrationOutlinedIcon from "@mui/icons-material/AppRegistrationOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link } from "react-router-dom";
import UserContext from "../../context/user/userContext";
import { useState, useContext, useEffect } from "react";

const theme = createTheme();

export const Register = (props) => {
  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(user);
    register(user);
  };

  const [emailError, setEmailError] = useState("");
  const validateEmail = (e) => {
    var email = e.target.value;

    if (validator.isEmail(email)) {
      setEmailError("Great! You have entered a valid email.");
    } else {
      setEmailError("Please enter a valid email! Requires @ and a domain");
    }
  };

  const userContext = useContext(UserContext);
  const { register, isLogin } = userContext;

  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
    gender: "",
    dob: new Date(),
    userStatus: "UNBLOCKED",
  });

  useEffect(() => {
    if (isLogin) {
      props.history.push("/");
    }
  }, [props.history, isLogin]);

  const { name, email, password, gender, dob } = user;

  const onChange = (e) => setUser({ ...user, [e.target.name]: e.target.value });

  const onChangeEmail = (e) => {setUser({ ...user, [e.target.name]: e.target.value }) ; validateEmail(e)};

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
            <AppRegistrationOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Register
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  autoComplete="given-name"
                  name="name"
                  value={name}
                  required
                  fullWidth
                  label="Username"
                  autoFocus
                  onChange={onChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  type="email"
                  label="Email Address"
                  value={email}
                  name="email"
                  //onChange={(e) => validateEmail(e)}
                  onChange={onChangeEmail}
                />
                <span
                  style={{
                    // fontWeight: "bold",
                    fontSize: "small",
                    color: "blue",
                  }}
                >
                  {emailError}
                </span>
                {/* <div
                  style={{
                    margin: "auto",
                    marginLeft: "300px",
                  }}
                >
                  <pre>
                    <span>Enter Email: </span>
                    <input
                      type="text"
                      id="userEmail"
                      onChange={(e) => validateEmail(e)}
                    ></input>{" "}
                    <br />
                    <span
                      style={{
                        fontWeight: "bold",
                        color: "red",
                      }}
                    >
                      {emailError}
                    </span>
                  </pre>
                </div> */}
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  value={password}
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  onChange={onChange}
                />
                <PasswordStrengthBar password={password} />
              </Grid>
              <Grid item xs={12}>
                <FormControl component="fieldset">
                  <FormLabel component="legend">Gender</FormLabel>
                  <RadioGroup
                    row
                    aria-label="gender"
                    name="gender"
                    onChange={onChange}
                    value={gender}
                  >
                    <FormControlLabel
                      value="male"
                      control={<Radio />}
                      label="Male"
                    />
                    <FormControlLabel
                      value="female"
                      control={<Radio />}
                      label="Female"
                    />
                  </RadioGroup>
                </FormControl>
              </Grid>
              {/* <Grid item xs={12}>
                <TextField
                  id="date"
                  label="Birthday"
                  type="date"
                  value={dob}
                  defaultValue="2017-05-24"
                  sx={{ width: 220 }}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  onChange={onChange}
                />
              </Grid> */}
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Register
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link to="/login" style={{ color: "#000000" }}>
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default Register;
