import React, { Component } from "react";
import { graphql } from "react-apollo";
import gql from "graphql-tag";
const Query = gql`
    query getPcs {
        get @rest(type: "GET", path: "mobile/pc") {
            id
        }
    }
`;
// class ShowsResult extends Component {
//     render() {
//         const {
//             data: { loading, error, data },
//         } = this.props;
//         if (loading) {
//             return <h4>Loading...</h4>;
//         }
//         if (error) {
//             return <h4>{error.message}</h4>;
//         }
//         return (
//             <div>
//                 <h1>{data}</h1>
//             </div>
//         );
//     }
// }
// const ShowsResultQuery = graphql(Query, {
//     options: ({ searchInput }) => {
//       return { variables: { searchInput } };
//     },
//   })(ShowsResult);

class ApolloTest extends Component {
    render() {
        const { loading, error, data } = this.props;
        if (loading) {
            return <h4>Loading...</h4>;
        }
        if (error) {
            return <h4>{error.message}</h4>;
        }
        return <h1>{data}</h1>;
    }
}

export default graphql(Query, {
    props: ({ data }) => {
        if (data.loading) {
            return {
                loading: data.loading,
            };
        }

        if (data.error) {
            return {
                error: data.error,
            };
        }
        return {
            data: data,
            loading: false,
        };
    },
})(ApolloTest);
