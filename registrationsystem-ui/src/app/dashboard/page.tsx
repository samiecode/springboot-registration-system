"use client";
import {signOut, useSession} from "next-auth/react";
import Image from "next/image";

export default function Dashboard() {
	const {data: session, status} = useSession();
	// @ts-ignore
	const fullName: string = session?.user.fullName;

	function capitalize(str: string) {
		return str
			.split(" ")
			.map((word) => word.charAt(0).toUpperCase() + word.slice(1))
			.join(" ");
	}

	if (status === "loading") {
		return <></>;
	} else {
		// @ts-ignore
		return (
			<main className="flex items-center justify-center h-screen relative">
				<button
					type="submit"
					className="absolute right-10 top-10 flex w-[130px] justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 transition duration-500"
					onClick={() => {
						signOut();
					}}
				>
					Logout
				</button>
				<div className="bg-white drop-shadow-md w-auto px-10 h-[200px] rounded-xl flex flex-col justify-center items-center gap-3">
					<div>
						<h1 className="font-semibold text-2xl">
							Hi {capitalize(fullName)}
						</h1>
					</div>

					<span className="italic">
						Thanks for testing my registration system
					</span>
				</div>
				<div className="w-full -mt-20 flex gap-4 absolute bottom-0 left-0 text-sm justify-center items-center">
					<a href="https://github.com/samieteq">
						<Image
							src="/github.png"
							height={35}
							width={35}
							alt=""
						/>
					</a>
					<a href="https://linkedin.com/in/samieteq">
						<Image
							src="/linkedin.png"
							height={35}
							width={35}
							alt=""
						/>
					</a>
					<a href="https://wa.me/+2348078960202">
						<Image
							src="/whatsapp.png"
							height={35}
							width={35}
							alt=""
						/>
					</a>
				</div>
			</main>
		);
	}
}
