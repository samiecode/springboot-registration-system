import NextAuth from "next-auth/next";

declare module "next-auth" {
	interface Session {
		user?: {
			id: number;
			fullName: string;
			email: string;
			roles: Array<string>;
			username: string;
			accessToken: string;
		};
	}
}
