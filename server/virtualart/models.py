from django.db import models
from django.contrib.auth.models import User
# Create your models here.


class Art(models.Model):
    class Meta:
        app_label = "virtualart"
        
    name = models.CharField(max_length=100)
    image = models.ImageField(upload_to="/image")
    longitude = models.FloatField()
    latitude = models.FloatField()
    pitch = models.FloatField()
    elevation = models.FloatField()
    #space = IntegerField(default=0, db_index=True)
    #zone = IntegerField(default=0, db_index=True)
    user = models.ForeignKey(User)


class SimpleArt(models.Model):
    class Meta: app_label = "virtualart"
    name = models.CharField(max_length=100, default="")
    coordinate = models.FloatField(default=0)
    
    def __unicode__(self):
        return self.name


class ComplexArt(models.Model):
    class Meta: app_label = "virtualart"
    name = models.CharField(max_length=100)
    image = models.TextField(default="")
    longitude = models.FloatField(default=0)
    latitude = models.FloatField(default=0)
    pitch = models.FloatField(default=0)
    elevation = models.FloatField(default=0)
    #space = IntegerField(default=0, db_index=True)
    #zone = IntegerField(default=0, db_index=True)
    #user = models.ForeignKey(User)
  
