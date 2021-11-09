import {
  USER_LOGINED,
  USER_FAILEDLOGIN,
  USER_REGISTER,
  USER_FAILEDREGISTER,
  GET_ALL_USERS,
  REFRESH,
  LOGOUT,
  EDIT_USER,
  BLOCK_USER,
  UNBLOCK_USER,
} from "../types";

export default (state, action) => {
  switch (action.type) {
    case USER_LOGINED:
      localStorage.setItem("token", JSON.stringify(action.payload));
      return {
        ...state,
        user: action.payload,
        isLogin: true,
      };
    case LOGOUT:
      localStorage.removeItem("token");
      return {
        ...state,
        token: null,
        isLogin: null,
        error: action.payload,
        user: null,
      };
    case USER_FAILEDLOGIN:
    case USER_FAILEDREGISTER:
      return {};
    case USER_REGISTER:
      return {
        ...state,
        user: action.payload,
        isLogin: true,
      };
    case GET_ALL_USERS:
      return {
        ...state,
        users: action.payload,
      };
    case EDIT_USER:
      return {
        ...state,
        user : action.payload,
        // users: state.users.map((user) =>
        //   user.id === action.payload.id ? action.payload : user
        // ),
      };
    case BLOCK_USER:
      return {
        ...state,
        users: state.users.map((user) =>
          user.id === action.payload.id ? action.payload : user
        ),
      };
    case UNBLOCK_USER:
      return {
        ...state,
        users: state.users.map((user) =>
          user.id === action.payload.id ? action.payload : user
        ),
      };
    case REFRESH:
      return {
        ...state,
        user: action.payload,
        isLogin: action.payload ? true : false
      };
    default:
      return state;
  }
};
