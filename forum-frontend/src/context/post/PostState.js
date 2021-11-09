import React, { useReducer } from "react";
import PostContext from "./postContext";
import postReducer from "./postReducer";
import axios from "axios";

import { GET_POST, ERROR_POST, GET_THREAD } from "../types";

const PostState = (props) => {
  const initialState = {
    //token: localStorage.getItem("token"),
    //user: [],
    post: {},
    thread: {},
    error: null,
  };

  const [state, dispatch] = useReducer(postReducer, initialState);

  const getPost = async (id) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.get(
        `http://localhost:8080/ForumSystem-war/webresources/posts/${id}`,
        config
      );
      dispatch({
        type: GET_POST,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_POST, payload: err.response.data.error });
    }
  };

  const getThread = async (id) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.get(
        `http://localhost:8080/ForumSystem-war/webresources/posts/${id}/threads`,
        config
      );
      dispatch({
        type: GET_THREAD,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_POST, payload: err.response.data.error });
    }
  };

  return (
    <PostContext.Provider
      value={{
        post: state.post,
        thread: state.thread,
        error: state.error,
        getPost,
        getThread,
      }}
    >
      {props.children}
    </PostContext.Provider>
  );
};

export default PostState;
