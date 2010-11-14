# encoding: utf-8
import datetime
from south.db import db
from south.v2 import SchemaMigration
from django.db import models

class Migration(SchemaMigration):

    def forwards(self, orm):
        
        # Adding model 'Art'
        db.create_table('virtualart_art', (
            ('id', self.gf('django.db.models.fields.AutoField')(primary_key=True)),
            ('name', self.gf('django.db.models.fields.CharField')(max_length=100)),
            ('image', self.gf('django.db.models.fields.files.ImageField')(max_length=100)),
            ('longitude', self.gf('django.db.models.fields.FloatField')()),
            ('latitude', self.gf('django.db.models.fields.FloatField')()),
            ('direction', self.gf('django.db.models.fields.FloatField')()),
            ('pitch', self.gf('django.db.models.fields.FloatField')()),
            ('elevation', self.gf('django.db.models.fields.FloatField')()),
        ))
        db.send_create_signal('virtualart', ['Art'])


    def backwards(self, orm):
        
        # Deleting model 'Art'
        db.delete_table('virtualart_art')


    models = {
        'virtualart.art': {
            'Meta': {'object_name': 'Art'},
            'direction': ('django.db.models.fields.FloatField', [], {}),
            'elevation': ('django.db.models.fields.FloatField', [], {}),
            'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'image': ('django.db.models.fields.files.ImageField', [], {'max_length': '100'}),
            'latitude': ('django.db.models.fields.FloatField', [], {}),
            'longitude': ('django.db.models.fields.FloatField', [], {}),
            'name': ('django.db.models.fields.CharField', [], {'max_length': '100'}),
            'pitch': ('django.db.models.fields.FloatField', [], {})
        }
    }

    complete_apps = ['virtualart']
