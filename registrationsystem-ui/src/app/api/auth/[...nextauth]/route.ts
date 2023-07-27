import NextAuth, {NextAuthOptions} from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import axios from "axios";
import {SERVER_URL} from "@/app/constant";

const authOptions : NextAuthOptions = {
    session: {
        strategy: "jwt"
    },
    secret: process.env.NEXTAUTH_SECRET,
    providers: [
        CredentialsProvider({
            type: 'credentials',
            credentials: {},
            async authorize(credentials, req) {

                const res = await axios({
                    method: "POST",
                    url: SERVER_URL + 'auth/login',
                    data: credentials
                })
                const user = await  res.data

                if (user) {
                    return user
                }
                return new Error("Invalid Credentials")
            }
        })
    ],

    callbacks: {
        async jwt({ token, user }) {
            return {...token, ...user};
        },
        async session({ session, token}) {
            session.user = token as any
            return session;
        },

    },
    pages: {
        signIn: "/",
    }
}

const handler = NextAuth(authOptions)

export {handler as GET, handler as POST};