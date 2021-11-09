import { AutoFixOffSharp } from "@mui/icons-material";
import { fabClasses } from "@mui/material";
import React, { useReducer } from "react";
import UserContext from "./userContext";
import userReducer from "./userReducer";
import axios from "axios";

import {
  USER_LOGINED,
  USER_FAILEDLOGIN,
  USER_REGISTER,
  USER_FAILEDREGISTER,
  GET_ALL_USERS,
  ERROR_USER,
  REFRESH,
  LOGOUT,
  EDIT_USER,
  BLOCK_USER,
  UNBLOCK_USER,
} from "../types";

const UserState = (props) => {
  const initialState = {
    //token: localStorage.getItem("token"),
    //user: [],
    user: JSON.parse(localStorage.getItem("token")) || {},
    users: [],
    error: null,
    isLogin: false,
  };

  const [state, dispatch] = useReducer(userReducer, initialState);

  const login = async (formData) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.post(
        "http://localhost:8080/ForumSystem-war/webresources/users/login",
        formData,
        config
      );
      dispatch({
        type: USER_LOGINED,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: USER_FAILEDLOGIN, payload: err.response.data.error });
    }
  };

  const register = async (formData) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.post(
        "http://localhost:8080/ForumSystem-war/webresources/users",
        formData,
        config
      );
      dispatch({
        type: USER_REGISTER,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: USER_FAILEDREGISTER, payload: err.response.data.error });
    }
  };

  const getUsers = async () => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.get(
        "http://localhost:8080/ForumSystem-war/webresources/users",
        config
      );
      dispatch({
        type: GET_ALL_USERS,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_USER, payload: err.response.data.error });
    }
  };

  const editUser = async (user) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        `http://localhost:8080/ForumSystem-war/webresources/users/${user.id}`,
        user,
        config
      );
      dispatch({
        type: EDIT_USER,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_USER, payload: err.response.data.error });
    }
  };

  const blockUser = async (user) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        "http://localhost:8080/ForumSystem-war/webresources/users/block",
        user,
        config
      );
      dispatch({
        type: BLOCK_USER,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_USER, payload: err.response.data.error });
    }
  };

  const unblockUser = async (user) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        "http://localhost:8080/ForumSystem-war/webresources/users/unblock",
        user,
        config
      );
      dispatch({
        type: UNBLOCK_USER,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_USER, payload: err.response.data.error });
    }
  };

  const refresh = async () => {
    dispatch({
      type: REFRESH,
      payload: JSON.parse(localStorage.getItem("token")),
    });
  };

  const logout = () =>
    dispatch({
      type: LOGOUT,
    });

  //register
  //getalluser
  //getuser

  return (
    <UserContext.Provider
      value={{
        user: state.user,
        error: state.error,
        isLogin: state.isLogin,
        users: state.users,
        login,
        logout,
        register,
        getUsers,
        refresh,
        editUser,
        blockUser,
        unblockUser,
      }}
    >
      {props.children}
    </UserContext.Provider>
  );
};

export default UserState;
