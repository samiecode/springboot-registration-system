import React from "react";
import {useField} from "formik";

// @ts-ignore
function TextField({...props}) {
	// @ts-ignore
	const [field, meta] = useField(props);
	return (
		<>
			<input {...props} {...field} required className={`${meta.touched && meta.error ? 'ring-red-600': null} focus:outline-none block w-full rounded-md border-0 py-2 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6`}/>
			{meta.touched && meta.error ? (
				<div className="text-red-600 text-[12px] transition duration-500 ease">
					{meta.error}
				</div>
			) : null}
		</>
	);
}

export default TextField;
