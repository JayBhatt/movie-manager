import { LoginForm } from "./login-form";

export interface RegistrationForm extends LoginForm {

    confirmPassword: string;

}