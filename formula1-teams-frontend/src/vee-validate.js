import { defineRule, configure, Field, Form, ErrorMessage } from 'vee-validate';
import { required, email, numeric, max, min } from '@vee-validate/rules';
import { localize, setLocale } from '@vee-validate/i18n';
import en from '@vee-validate/i18n/dist/locale/en.json';

// Define validation rules
export const setupVeeValidate = (app) => {
    defineRule('required', required);

    defineRule('email', email);
    defineRule('numeric', numeric);
    defineRule('max', max);
    defineRule('min', min);

    configure({
        generateMessage: localize({
            en: {
                names: {
                    name: 'Name',
                    yearFounded: 'Year founded',
                    championshipsWon: 'Championships won'
                }
            }
        })
    });

    configure({
        generateMessage: localize({
            en
        })
    });

    // Set the active locale to Hungarian
    setLocale('en');

    app.component('Field', Field);
    // eslint-disable-next-line vue/no-reserved-component-names
    app.component('Form', Form);
    app.component('ErrorMessage', ErrorMessage);
};
