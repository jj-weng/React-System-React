import React, { useReducer } from "react";
import ForumContext from "./forumContext";
import forumReducer from "./forumReducer";
import axios from "axios";

import {
  GET_ALL_FORUMS,
  GET_FORUM,
  ERROR_FORUM,
  ERROR_THREAD,
  CREATE_FORUM,
  EDIT_FORUM,
  EDIT_THREAD,
  DELETE_THREAD,
  DELETE_FORUM,
  OPEN_THREAD,
  CLOSE_THREAD,
  CREATE_THREAD,
} from "../types";

const ForumState = (props) => {
  const initialState = {
    //token: localStorage.getItem("token"),
    //user: [],
    forum: {},
    forums: [],
    threads: [],
    forum: {},
    error: null,
  };

  const [state, dispatch] = useReducer(forumReducer, initialState);

  const getForums = async () => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.get(
        "http://localhost:8080/ForumSystem-war/webresources/forums",
        config
      );
      dispatch({
        type: GET_ALL_FORUMS,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_FORUM, payload: err.response.data.error });
    }
  };

  const getForum = async (id) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.get(
        `http://localhost:8080/ForumSystem-war/webresources/forums/${id}`,
        config
      );
      dispatch({
        type: GET_FORUM,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_FORUM, payload: err.response.data.error });
    }
  };

  const createForum = async (formData) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.post(
        "http://localhost:8080/ForumSystem-war/webresources/forums",
        formData,
        config
      );
      dispatch({
        type: CREATE_FORUM,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_FORUM, payload: err.response.data.error });
    }
  };

  const editForum = async (forum) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        `http://localhost:8080/ForumSystem-war/webresources/forums/${forum.id}`,
        forum,
        config
      );
      dispatch({
        type: EDIT_FORUM,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_FORUM, payload: err.response.data.error });
    }
  };

  const editThread = async (thread) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        `http://localhost:8080/ForumSystem-war/webresources/threads/${thread.id}`,
        thread,
        config
      );
      dispatch({
        type: EDIT_THREAD,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_THREAD, payload: err.response.data.error });
    }
  };

  const deleteForum = async (forum) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/ForumSystem-war/webresources/forums/${forum.id}`,
        forum,
        config
      );
      dispatch({
        type: DELETE_FORUM,
        payload: forum.id,
      });
    } catch (err) {
      dispatch({ type: ERROR_FORUM, payload: err.response.data.error });
    }
  };

  const deleteThread = async (thread) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/ForumSystem-war/webresources/threads/${thread.id}`,
        thread,
        config
      );
      dispatch({
        type: DELETE_THREAD,
        payload: thread.id,
      });
    } catch (err) {
      dispatch({ type: ERROR_THREAD, payload: err.response.data.error });
    }
  };

  const closeThread = async (thread) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        "http://localhost:8080/ForumSystem-war/webresources/threads/close",
        thread,
        config
      );
      dispatch({
        type: CLOSE_THREAD,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_THREAD, payload: err.response.data.error });
    }
  };

  const openThread = async (thread) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        "http://localhost:8080/ForumSystem-war/webresources/threads/open",
        thread,
        config
      );
      dispatch({
        type: OPEN_THREAD,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_THREAD, payload: err.response.data.error });
    }
  };

  const createThread = async (thread, userId, forumId) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.post(
        `http://localhost:8080/ForumSystem-war/webresources/threads/${userId}/${forumId}`,
        thread,
        config
      );
      dispatch({
        type: CREATE_THREAD,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_THREAD, payload: err.response.data.error });
    }
  };

  return (
    <ForumContext.Provider
      value={{
        forum: state.forum,
        forums: state.forums,
        thread: state.thread,
        threads: state.threads,
        error: state.error,
        getForums,
        getForum,
        createForum,
        editForum,
        editThread,
        deleteThread,
        deleteForum,
        openThread,
        closeThread,
        createThread,
      }}
    >
      {props.children}
    </ForumContext.Provider>
  );
};

export default ForumState;
