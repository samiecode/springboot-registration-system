'use client'

import {Form, Formik} from "formik";
import TextField from "@/components/TextField";
import * as Yup from 'yup'
import {set} from "zod";
import {signIn, useSession} from "next-auth/react";
import {useRouter} from "next/navigation";
import {registerUser} from "@/app/api/client";
import {toast} from "react-hot-toast";
import {useEffect} from "react";


export default function Signup() {
    const router = useRouter();
    const {data: session, status} = useSession();

    useEffect(()=>{
        if(status === "authenticated"){
            router.push("/dashboard")
        }
    })

    return(
        <main className="flex min-h-screen flex-col items-center justify-between sm:p-24 p-5">

            <div className="flex min-h-full flex-col justify-center px-12 bg-white drop-shadow-md rounded-xl py-16 ">
                <div className="sm:mx-auto sm:w-full sm:max-w-sm">
                    <h2 className="text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Create your account</h2>
                </div>

                <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">

                    <Formik
                        initialValues={{
                            fullName: '',
                            email: '',
                            password: ''
                        }}
                        validationSchema={Yup.object({
                            fullName: Yup.string().required("Full name is required*"),
                            email: Yup.string().email().required("Email is required*"),
                            password: Yup.string().required("Password is required*")
                        })}
                        onSubmit={(values,{setSubmitting})=>{
                            setSubmitting(true)
                            // @ts-ignore
                            registerUser(values)
                                .then((res)=>{
                                    toast.success(res.data, {
                                        duration: 4000
                                    });
                                    signIn('credentials', {
                                        redirect: false,
                                        username: values.email,
                                        password: values.password
                                    })


                                })
                                .catch((err)=>{
                                    toast.error(err.response.data.message ,{
                                        duration:5000
                                    });
                                })

                            setSubmitting(false);

                        }}>
                        {({isSubmitting}:{isSubmitting:boolean})=>(
                            <Form className="space-y-6">
                                <div>
                                    <label className="block text-sm font-medium leading-6 text-gray-900">Full name</label>
                                    <div className="mt-2">
                                        <TextField name="fullName" type="text"/>
                                    </div>
                                </div>
                                <div>
                                    <label className="block text-sm font-medium leading-6 text-gray-900">Email address</label>
                                    <div className="mt-2">
                                        <TextField name="email" type="email"/>
                                    </div>
                                </div>

                                <div>
                                    <div className="flex items-center justify-between">
                                        <label className="block text-sm font-medium leading-6 text-gray-900">Password</label>
                                    </div>
                                    <div className="mt-2">
                                        <TextField name="password" type="password"/>
                                    </div>
                                </div>

                                <div>
                                    <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 transition duration-500">{isSubmitting ? 'Sending...' : 'Create Account'}</button>
                                </div>
                            </Form>
                        )}
                    </Formik>

                    <p className="mt-10 text-center text-sm text-gray-500">
                         Already Register?
                        <a href="/" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500"> Sign in your account</a>
                    </p>
                </div>
            </div>

        </main>
    );
}