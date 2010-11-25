# Create your views here.
from models import *
from django.shortcuts import get_object_or_404, render_to_response
from django.http import HttpResponseRedirect, HttpResponse
from django.core.urlresolvers import reverse

def simpleart_get(request):
    return None

def see_request(request):
    print '-'*30
    print dir(request)
    print '-'*30
    print request
    print '-'*30
    print "REQ", request.REQUEST
    print "FILE", request.FILES
    print "POST", request.POST
    print "POST", request.POST.items()
