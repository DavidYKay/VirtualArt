# Create your views here.
from django.shortcuts import render_to_response
from django import forms
from django.http import HttpResponseRedirect

class ContactForm(forms.Form):
    name = forms.CharField(max_length=100)
    latitude = forms.FloatField()
    longitude = forms.FloatField()
    elevation = forms.FloatField()
    direction = forms.FloatField()
    image = forms.ImageField()
    pitch = forms.FloatField()
   
                
def add_art(request):
    if request.method == 'POST': # If the form has been submitted...
        form = ContactForm(request.POST) # A form bound to the POST data
        print dir(form)
        print form.data
        print form.files
        if form.is_valid(): # All validation rules pass
            # Process the data in form.cleaned_data
            # ...
            print "VALID"
            print form.cleaned_data
            return HttpResponseRedirect('/thanks/') # Redirect after POST
        else:
            print form
    else:
        print "no"
        form = ContactForm() # An unbound form

    return render_to_response('add.html', {
        'form': form,
    })
