import NextAuth from "next-auth/next";
import {DefaultSession} from "next-auth";

declare module "next-auth" {
	interface Session {
		user?: {
			id: number;
			fullName: string;
			email: string;
			roles: Array<string>;
			username: string;
			accessToken: string;
		} & DefaultSession;
	}
}
