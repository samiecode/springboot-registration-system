'use client'

import {Form, Formik} from "formik";
import {signIn, useSession} from "next-auth/react";
import * as Yup from 'yup'
import {useRouter} from "next/navigation";
import TextField from "@/components/TextField";
import {toast} from "react-hot-toast";
import {useEffect} from 'react'

export default function Home() {
  const router = useRouter();
  const {data: session, status} = useSession();
  
    useEffect(()=>{
        if(status === "authenticated"){
            router.push('/dashboard');
        }
    })
  return (
    <main className="flex min-h-screen flex-col items-center justify-between sm:p-24 p-5">

      <div className="flex min-h-full flex-col justify-center px-10 bg-white drop-shadow-md rounded-xl py-10 ">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 className="text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Sign in to your account</h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <Formik
              initialValues={{
                email: '',
                password: ''
              }}
              validationSchema={Yup.object({
                email: Yup.string().email().required("Email is required*"),
                password: Yup.string().required("Password is required*")
              })}
              onSubmit={async (values, {setSubmitting}) => {

                  const success = await signIn('credentials', {
                      redirect: false,
                      username: values.email,
                      password: values.password
                  })
                  // @ts-ignore
                  if(success.error) {
                      // @ts-ignore
                      toast.error("Invalid Credentials!");
                     
                  }
                  else {
                      toast.success("Successfully Login!")
                  }
                  /*signIn('credentials', {
                    redirect: false,
                    username: values?.email,
                    password: values?.password
                  }).then(r => {
                    router.push('/dashboard');
                  }).catch((err)=>{
                      toast.error("Invalid Credentials!")
                  })
                  setSubmitting(false);*/
              }}>
            {({isSubmitting}:{isSubmitting:boolean})=>(
              <Form className="space-y-6">
                <div>
                  <label className="block text-sm font-medium leading-6 text-gray-900">Email address</label>
                  <div className="mt-2">
                  <TextField name="email" type="email"/>
                </div>
                </div>

              <div>
              <div className="flex items-center justify-between">
              <label className="block text-sm font-medium leading-6 text-gray-900">Password</label>
              <div className="text-sm">
              <a href="#" className="font-semibold text-indigo-600 hover:text-indigo-500">Forgot password?</a>
              </div>
              </div>
              <div className="mt-2">
              <TextField name="password" type="password"/>
              </div>
              </div>

              <div>
              <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">{isSubmitting ? 'Sending...' : 'Sign in'}</button>
              </div>
              </Form>
            )}
          </Formik>

          <p className="mt-10 text-center text-sm text-gray-500">
            Not Register?
            <a href="/signup" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500"> Create account</a>
          </p>
        </div>
      </div>

    </main>
  );
}
