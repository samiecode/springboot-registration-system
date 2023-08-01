import './globals.css'
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import NextAuthProvider from "@/components/Provider";
import {Toaster} from 'react-hot-toast'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Sign in | Samie Azubike',
  description: 'Generated by create next app',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
		<html lang="en">
			<body className={inter.className}>
			{/*
				<div className="w-full py-2 text-white absolute bg-indigo-600 top-0 left-0 text-center text-[12px]">
					<p>
						Notice: I&apos;m trying to deploy the backend to AWS, so
						it may not be working properly with the frontend now, but it working locally
					</p>
				</div>
			*/}
				<NextAuthProvider>
					<Toaster />
					{children}
				</NextAuthProvider>
			</body>
		</html>
  );
}
