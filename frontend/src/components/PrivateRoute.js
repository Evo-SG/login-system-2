import {Redirect, Route} from "react-router-dom";
import jwt_decode from "jwt-decode";

export default function PrivateRoute({ children, permission, ...rest}) {
    let role;
    const token = localStorage.getItem('accessToken');

    if (token)
    {
        role = jwt_decode(token).username;
    }

    const auth = role === permission;

    return (
        <Route
            {...rest}
            render={({ location }) =>
                auth ? (
                    children
                ) : (
                    <Redirect
                        to={{
                            pathname: "/login",
                            state: { from: location }
                        }}
                    />
                )
            }
        />
    )
}