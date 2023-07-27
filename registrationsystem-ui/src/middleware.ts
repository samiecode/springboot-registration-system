import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
//import {default} from "next-auth/middleware"
 import {withAuth} from "next-auth/middleware";

 
// @ts-ignore
export default withAuth(
	function middleware(req){
		return NextResponse.rewrite(new URL("/dashboard", req.url));
	},
	{
		callbacks:{
			authorized({req,token}){
				if (token?.role === "USER") {
					return true
				}
				if (token?.role === "ADMIN") return true;
			}
		}
	}
);

export const config = {
	//matcher: ["/", "/((?!signup|api|login).*)" ]
	matcher: ["/dashboard"],
};
