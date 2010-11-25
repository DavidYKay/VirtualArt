from django.conf.urls.defaults import *
from views import *

urlpatterns = patterns('',
    (r'^see_request/$', see_request),
    #(r'^simpleart/get/$', simpleart_get),
    #(r'^complexart/get/$', complexart_get),
)
