import {
  ERROR_FORUM,
  GET_ALL_FORUMS,
  GET_FORUM,
  CREATE_FORUM,
  CREATE_THREAD,
  EDIT_FORUM,
  EDIT_THREAD,
  DELETE_FORUM,
  DELETE_THREAD,
  OPEN_THREAD,
  CLOSE_THREAD,
} from "../types";

export default (state, action) => {
  switch (action.type) {
    case GET_ALL_FORUMS:
      return {
        ...state,
        forums: action.payload,
      };
    case GET_FORUM:
      return {
        ...state,
        forum: action.payload,
        threads: action.payload.threads,
      };
    case CREATE_FORUM:
      return {
        ...state,
        forums: [action.payload, ...state.forums],
      };
    case CREATE_THREAD:
      return {
        ...state,
        threads: [action.payload, ...state.threads],
      };
    case EDIT_FORUM:
      return {
        ...state,
        forum : action.payload,
        // forums: state.forums.map((forum) =>
        //   forum.id === action.payload.id ? action.payload : forum
        // ),
      };
    case EDIT_THREAD:
      return {
        ...state,
        thread : action.payload,
        // threads: state.threads.map((thread) =>
        //   thread.id === action.payload.id ? action.payload : thread
        // ),
      };
    case DELETE_FORUM:
      return {
        ...state,
        forums: state.forums.filter((forum) => forum.id !== action.payload),
      };
    case DELETE_THREAD:
      return {
        ...state,
        threads: state.threads.filter((thread) => thread.id !== action.payload),
      };
    case CLOSE_THREAD:
      return {
        ...state,
        threads: state.threads.map((thread) =>
          thread.id === action.payload.id ? action.payload : thread
        ),
      };
    case OPEN_THREAD:
      return {
        ...state,
        threads: state.threads.map((thread) =>
          thread.id === action.payload.id ? action.payload : thread
        ),
      };
    default:
      return state;
  }
};
